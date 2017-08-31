package it.polimi.jasper.engine;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import it.polimi.jasper.engine.instantaneous.InstantaneousGraph;
import it.polimi.jasper.engine.instantaneous.InstantaneousGraphBase;
import it.polimi.jasper.engine.instantaneous.InstantaneousModelCom;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.jasper.engine.sds.JenaSDSImpl;
import it.polimi.jasper.engine.stream.GraphStreamItem;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.yasper.core.engine.RSPQLEngine;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredQueryExeception;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.exceptions.UnsuportedQueryClassExecption;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import it.polimi.yasper.core.stream.QueryStream;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.timevarying.DefaultTVG;
import it.polimi.yasper.core.timevarying.NamedTVG;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.riot.system.IRIResolver;
import org.parboiled.Parboiled;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import java.util.*;

@Log4j
public class JenaRSPQLEngineImpl extends RSPQLEngine {

    @Getter
    private IRIResolver resolver;
    private  RSPQLParser parser;

    public JenaRSPQLEngineImpl(long t0, EngineConfiguration ec) {
        super(t0, ec);
        resolver = IRIResolver.create(ec.getBaseURI());
        parser = Parboiled.createParser(RSPQLParser.class);
        parser.setResolver(resolver);
    }

    public JenaRSPQLEngineImpl(long t0, String baseUri) {
        this(t0, EngineConfiguration.getDefault());
    }

    @Override
    public Stream register(Stream s) {
        log.info("Registering Stream [" + s.getURI() + "]");
        EPStatement e = createStream(s.toEPLSchema(), s.getURI());
        registeredStreams.put(s.getURI(), s);
        return new RegisteredStream(s, e);
    }

    @Override
    public void unregister(Stream s) {
        log.info("Unregistering Stream [" + s + "]");
        EPStatement statement = cepAdm.getStatement(EncodingUtils.encode(s.getURI()));
        statement.removeAllListeners();
        statement.destroy();
        Stream remove = registeredStreams.remove(EncodingUtils.encode(s.getURI()));
    }

    @Override
    public ContinuousQueryExecution register(String q, QueryConfiguration c) {
        return register(parseQuery(q), c);
    }

    @Override
    public ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c) {
        String tboxLocation = c.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        Maintenance maintenance = c.getSdsMaintainance();
        Entailment entailment = c.getReasoningEntailment();
        if ("it.polimi.jasper.engine.query.RSPQuery".equals(c.getQueryClass())) {
            return register((RSPQuery) q, tbox, maintenance, entailment, rsp_config.isRecursionEnables());
        } else {
            throw new UnsuportedQueryClassExecption();
        }
    }

    public ContinuousQueryExecution register(RSPQuery bq, Model tbox, Maintenance maintenance, Entailment entailment, boolean recursionEnabled) {
        log.info("Registering Query [" + bq.getName() + "]");

        registeredQueries.put(bq.getID(), bq);
        queryObservers.put(bq.getID(), new ArrayList<QueryResponseFormatter>());

        log.info(bq.getQ().toString());

        if (bq.getHeader() != null) {
            register(new QueryStream(this, bq.getID()));
        }

        if (bq.isRecursive() && !recursionEnabled) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }

        Model def = loadStaticGraph(bq, new ModelCom(new InstantaneousGraphBase()));

        JenaTVGReasoner reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailment, tbox);

        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(def.getGraph()));

        JenaSDS sds = new JenaSDSImpl(tbox, kb_star, bq.getResolver(), maintenance, "", cep, this);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.create(bq, sds, reasoner);

        sds.addQueryExecutor(bq, qe);

        addNamedStaticGraph(bq, sds, reasoner);
        addWindows(bq, sds, reasoner);
        addNamedWindows(sds, bq, reasoner);

        assignedSDS.put(bq.getID(), sds);
        registeredQueries.put(bq.getID(), bq);
        queryExecutions.put(bq.getID(), qe);

        return qe;
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
                    ceq.removeObserver(f);
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
            queryExecutions.get(qId).removeObserver(o);
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
        cqe.removeObserver(o);
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

    private void addWindows(RSPQuery bq, JenaSDS sds, JenaTVGReasoner reasoner) {
        //Default Time-Varying Graph
        int i = 0;

        TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(new InstantaneousGraphBase());

        sds.addDefaultWindow(new InstantaneousModelCom(bind)); //JenaSDS

        DefaultTVG defTVG = new DefaultTVG(sds.getMaintenanceType(), bind);

        sds.addTimeVaryingGraph(defTVG);

        if (bq.getWindows() != null) {
            for (Window window : bq.getWindows()) {
                String stream = EncodingUtils.encode(window.getStreamURI());
                String statementName = "QUERY" + "STMT_" + i;
                createWindow(t0, window.getOmega().longValue(), window.getBeta().longValue(), window.getStream().toEPLSchema());
                defTVG.addStatement(getEpStatement(sds, window, statementName));
                sds.addDefaultWindowStream(stream);
                i++;
            }
        }
    }

    private void addNamedWindows(JenaSDS sds, RSPQuery bq, JenaTVGReasoner reasoner) {
        int j = 0;
        if (bq.getNamedwindows() != null) {
            for (Map.Entry<Node, Window> entry : bq.getNamedwindows().entrySet()) {
                Window w = entry.getValue();

                String epl_stream_uri = w.getStreamURI(); //name of the stream in the query
                String window_uri = w.getIri().getURI(); //name of the window in the query

                String query_id = bq.getID();
                String epl_statement_name = "QUERY_" + query_id + "_STATEMENT" + j;

                if (!checkStreamExistence(epl_stream_uri)) {
                    throw new UnregisteredStreamExeception(w.getStreamURI());
                }

                log.info("creating named graph " + window_uri + "");

                InstantaneousGraph bind = (InstantaneousGraph) reasoner.bind(new InstantaneousGraphBase());

                WindowOperator wo = getEpStatement(sds, w, epl_statement_name);
                NamedTVG tvg = new NamedTVG(sds.getMaintenanceType(), bind, wo);

                sds.addNamedTimeVaryingGraph(window_uri, tvg);//SDS
                sds.addNamedWindowStream(window_uri, new InstantaneousModelCom(bind));//JenaSDS

                j++;
            }
        }
    }

    private WindowOperator getEpStatement(JenaSDS sds, Window w, String epl_statement_name) {
        WindowOperator wo;
        if (Maintenance.INCREMENTAL.equals(sds.getMaintenanceType())) {
            wo = createWindow(t0, w.getOmega().longValue(), w.getBeta().longValue(), w.toIREPL(), epl_statement_name);
            log.info(w.toIREPL().toEPL());
        } else {
            wo = createWindow(t0, w.getOmega().longValue(), w.getBeta().longValue(), w.toEPL(), epl_statement_name);
            log.info(w.toEPL().toEPL());
        }
        return wo;
    }

    private void addNamedStaticGraph(RSPQuery bq, JenaSDS sds, JenaTVGReasoner reasoner) {
        //Named Static Graphs
        if (bq.getNamedGraphURIs() != null)
            for (String g : bq.getNamedGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getNamedwindows().keySet(), g)) {
                    Model m = ModelFactory.createDefaultModel().read(g);
                    TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(m.getGraph());
                    sds.addNamedModel(g, new InfModelImpl(bind));
                }
            }
    }

    private Model loadStaticGraph(RSPQuery bq, Model def) {
        //Default Static Graph
        if (bq.getGraphURIs() != null)
            for (String g : bq.getGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getWindows(), g)) {
                    def = def.read(g);
                }
            }
        return def;
    }

    protected boolean isWindow(Set<?> windows, String g) {
        if (windows != null) {
            Iterator<?> iterator = windows.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof Window && ((Window) next).getStreamURI().equals(g)) {
                    return true;
                } else if (next instanceof Node && ((Node) next).getURI().equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }
}