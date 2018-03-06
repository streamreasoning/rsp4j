package it.polimi.jasper.engine;

import it.polimi.jasper.engine.querying.RSPQuery;
import it.polimi.jasper.engine.reasoning.EntailmentImpl;
import it.polimi.jasper.engine.reasoning.ReasoningUtils;
import it.polimi.jasper.engine.sds.JasperSDSBuilder;
import it.polimi.jasper.engine.spe.EsperRSPEngine;
import it.polimi.jasper.engine.spe.esper.EsperStreamRegistrationService;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.yasper.core.enums.EntailmentType;
import it.polimi.yasper.core.exceptions.UnregisteredQueryExeception;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDSBuilder;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.reasoning.Entailment;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.system.IRIResolver;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class Jasper extends EsperRSPEngine {

    private HashMap<String, Entailment> entailments;
    @Getter
    private IRIResolver resolver;
    private RSPQLParser parser;

    EsperStreamRegistrationService schemaAssigner;

    public Jasper(long t0, EngineConfiguration ec) {
        super(t0, ec);
        this.resolver = IRIResolver.create(ec.getBaseURI());
        this.parser = Parboiled.createParser(RSPQLParser.class);
        this.parser.setResolver(resolver);

        ReasonerRegistry.getRDFSSimpleReasoner();

        this.entailments = new HashMap<>();

        //Adding default entailments
        String ent = EntailmentType.RDFS.name();
        this.entailments.put(ent, new EntailmentImpl(ent, Rule.rulesFromURL(ReasoningUtils.RHODF_RULE_SET_RUNTIME), EntailmentType.RDFS));
        ent = EntailmentType.RHODF.name();
        this.entailments.put(ent, new EntailmentImpl(ent, Rule.rulesFromURL(ReasoningUtils.RHODF_RULE_SET_RUNTIME), EntailmentType.RHODF));
        this.schemaAssigner = new EsperStreamRegistrationService(admin);
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return register(parseQuery(q), c);
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        Map<String, Stream> registeredStreams = stream_registration_service.getRegisteredStreams();
        SDSBuilder builder = new JasperSDSBuilder(registeredStreams, entailments, rsp_config, c);
        q.accept(builder);
        ContinuousQueryExecution continuousQueryExecution = builder.getContinuousQueryExecution();
        save(q, continuousQueryExecution, builder.getSDS());
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
                    ceq.deleteFormatter(f);
                }
            }
            assignedSDS.remove(query.getID());
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
            ceq.addFormatter(o);
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
            queryExecutions.get(qId).deleteFormatter(o);
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
            ceq.addFormatter(o);
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
        cqe.deleteFormatter(o);
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