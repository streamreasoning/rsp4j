package it.polimi.jasper.engine;

import com.espertech.esper.client.EPStatement;
import it.polimi.jasper.engine.query.JenaSDSQueryBuilder;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.stream.RDFStream;
import it.polimi.jasper.engine.stream.RegisteredRDFStream;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.engine.RSPQLEngineImpl;
import it.polimi.yasper.core.exceptions.UnregisteredQueryExeception;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.riot.system.IRIResolver;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.yasper.core.query.operators.s2r.EPLFactory.toEPLSchema;

@Log4j
public class JenaRSPQLEngineImpl extends RSPQLEngineImpl<RDFStream, RegisteredRDFStream> {

    @Getter
    private IRIResolver resolver;
    private RSPQLParser parser;

    public JenaRSPQLEngineImpl(long t0, EngineConfiguration ec) {
        super(t0, ec);
        resolver = IRIResolver.create(ec.getBaseURI());
        parser = Parboiled.createParser(RSPQLParser.class);
        parser.setResolver(resolver);

    }

    @Override
    public RegisteredRDFStream register(RDFStream s) {
        /* NOTE in a federated context I may have several SDS maintained by the same RSPEngine as a manager.
           The stream registration, which happens before the query registration,
         has two option then lazy approach that waits for the first query using the stream to decide where
          (SDS - RSP Engine) locate this Stream, or can use metadata found in the vois file. */
        log.info("Registering Stream [" + s.getURI() + "]");
        EPStatement epl = createStream(toEPLSchema(s), s.getURI());
        RegisteredRDFStream value = new RegisteredRDFStream(s, epl, this);
        registeredStreams.put(s.getURI(), value);
        return value;
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return register(parseQuery(q), c);
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        SDSBuilder builder = new JenaSDSQueryBuilder(cepAdm, cep, registeredStreams, rsp_config, c);
        q.accept(builder);
        ContinuousQueryExecution continuousQueryExecution = builder.getContinuousQueryExecution();
        persist(q, continuousQueryExecution, builder.getSDS());
        //register(new QueryStream(this, q.getID(), RDFStreamItem.class));
        return continuousQueryExecution;
    }

    @Override
    public void unregister(ContinuousQuery q) {
        String qId = q.getID();
        if (registeredQueries.containsKey(qId)) {
            ContinuousQuery query = registeredQueries.remove(qId);
            ContinuousQueryExecution ceq = queryExecutions.remove(qId);
            List<QueryResponseFormatter> l = queryObservers.remove(qId);
            if (l != null) {
                for (QueryResponseFormatter f : l) {
                    ceq.deleteObserver(f);
                }
            }
            assignedSDS.remove(query);
        } else
            throw new UnregisteredQueryExeception(qId);
    }

    @Override
    public void register(ContinuousQuery q, QueryResponseFormatter o) {
        String qID = q.getID();
        log.info("Registering Observer [" + o.getClass() + "] to Query [" + qID + "]");
        if (!registeredQueries.containsKey(qID))
            throw new UnregisteredQueryExeception(qID);
        else {
            ContinuousQueryExecution ceq = queryExecutions.get(qID);
            ceq.addObserver(o);
            if (queryObservers.containsKey(qID)) {
                List<QueryResponseFormatter> l = queryObservers.get(qID);
                if (l != null) {
                    l.add(o);
                } else {
                    l = new ArrayList<>();
                    l.add(o);
                    queryObservers.put(qID, l);
                }
            }
        }
    }

    @Override
    public void unregister(ContinuousQuery q, QueryResponseFormatter o) {
        String qId = q.getID();
        log.info("Unregistering Observer [" + o.getClass() + "] from Query [" + qId + "]");
        if (queryExecutions.containsKey(qId)) {
            queryExecutions.get(qId).deleteObserver(o);
            if (queryObservers.containsKey(qId)) {
                queryObservers.get(qId).remove(o);
            }
        }
        throw new UnregisteredQueryExeception(qId);
    }

    @Override
    public void register(ContinuousQueryExecution ceq, QueryResponseFormatter o) {
        String qID = ceq.getQueryID();
        log.info("Registering Observer [" + o.getClass() + "] to Query [" + qID + "]");
        if (!registeredQueries.containsKey(qID))
            throw new UnregisteredQueryExeception(qID);
        else {
            ceq.addObserver(o);
            if (queryObservers.containsKey(qID)) {
                List<QueryResponseFormatter> l = queryObservers.get(qID);
                if (l != null) {
                    l.add(o);
                } else {
                    l = new ArrayList<>();
                    l.add(o);
                    queryObservers.put(qID, l);
                }
            }
        }
    }

    @Override
    public void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o) {
        cqe.deleteObserver(o);
        if (queryObservers.containsKey(cqe.getQueryID())) {
            queryObservers.get(cqe.getQueryID()).remove(o);
            throw new UnregisteredQueryExeception(cqe.getQueryID());
        }
    }


    @Override
    public ContinuousQuery parseQuery(String input) {
        log.info("Parsing Query [" + input + "]");

        ParsingResult<RSPQuery> result = new ReportingParseRunner(parser.Query()).run(input);

        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                log.info(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        RSPQuery query = result.resultValue;
        log.info("Final Query [" + query + "]");
        return query;
    }
}