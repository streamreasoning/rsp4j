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
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.timevarying.DefaultTVG;
import it.polimi.yasper.core.timevarying.NamedTVG;
import it.polimi.yasper.core.utils.EncodingUtils;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
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

    public JenaRSPQLEngineImpl(long t0, EngineConfiguration ec) {
        super(t0, ec);
        StreamItem typeMap = new GraphStreamItem();
        log.info("Added [" + typeMap.getClass() + "] as TStream");
        cep_config.addEventType("TStream", typeMap);
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cep_config);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();
    }

    public JenaRSPQLEngineImpl(long t0) {
        this(t0, EngineConfiguration.getDefault());
    }

    @Override
    public void registerStream(Stream s) {
        log.info("Registering Stream [" + s.getURI() + "]");
        s.setRSPEngine(this);
        createStream(s.toEPLSchema(), s.getURI());
        registeredStreams.put(s.getURI(), s);
    }

    @Override
    public void unregisterStream(String s) {
        log.info("Unregistering Stream [" + s + "]");
        EPStatement statement = cepAdm.getStatement(EncodingUtils.encode(s));
        statement.removeAllListeners();
        statement.destroy();
        Stream remove = registeredStreams.remove(EncodingUtils.encode(s));
        remove.setRSPEngine(null);
    }

    @Override
    public ContinuousQueryExecution registerQuery(String q, QueryConfiguration c) {
        return registerQuery(parseQuery(q), c);
    }

    @Override
    public ContinuousQueryExecution registerQuery(ContinuousQuery q, QueryConfiguration c) {
        String tboxLocation = c.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        Maintenance maintenance = c.getSdsMaintainance();
        Entailment entailment = c.getReasoningEntailment();
        if ("it.polimi.jasper.engine.query.RSPQuery".equals(c.getQueryClass())) {
            return registerQuery((RSPQuery) q, tbox, maintenance, entailment, rsp_config.isRecursionEnables());
        } else {
            throw new UnsuportedQueryClassExecption();
        }
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, Model tbox, Maintenance maintenance, Entailment entailment, boolean recursionEnabled) {
        log.info("Registering Query [" + bq.getName() + "]");

        registeredQueries.put(bq.getID(), bq);
        queryObservers.put(bq.getID(), new ArrayList<QueryResponseFormatter>());

        log.info(bq.getQ().toString());

        if (bq.getHeader() != null) {
            registerStream(new QueryStream(this, bq.getID()));
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
    public void unregisterQuery(String qId) {
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
    public void registerObserver(String q, QueryResponseFormatter o) {
        log.info("Registering Observer [" + o.getClass() + "] to Query [" + q + "]");
        if (!registeredQueries.containsKey(q))
            throw new UnregisteredQueryExeception(q);
        else {
            ContinuousQueryExecution ceq = queryExecutions.get(q);
            ceq.addObserver(o);
            if (queryObservers.containsKey(q)) {
                List<QueryResponseFormatter> l = queryObservers.get(q);
                if (l != null) {
                    l.add(o);
                } else {
                    l = new ArrayList<>();
                    l.add(o);
                    queryObservers.put(q, l);
                }
            }
        }

    }

    @Override
    public void unregisterObserver(String q, QueryResponseFormatter o) {
        log.info("Unregistering Observer [" + o.getClass() + "] from Query [" + q + "]");
        if (queryExecutions.containsKey(q)) {
            queryExecutions.get(q).removeObserver(o);
            if (queryObservers.containsKey(q)) {
                queryObservers.get(q).remove(o);
            }
        }
        throw new UnregisteredQueryExeception(q);
    }

    @Override
    public ContinuousQuery parseQuery(String input) {
        log.info("Parsing Query [" + input + "]");

        RSPQLParser parser = Parboiled.createParser(RSPQLParser.class);

        parser.setResolver(IRIResolver.create());

        ParsingResult<RSPQuery> result = new ReportingParseRunner(parser.Query()).run(input);

        if (result.hasErrors()) {
            for (ParseError arg : result.parseErrors) {
                System.out.println(input.substring(0, arg.getStartIndex()) + "|->" + input.substring(arg.getStartIndex(), arg.getEndIndex()) + "<-|" + input.substring(arg.getEndIndex() + 1, input.length() - 1));
            }
        }
        RSPQuery query = result.resultValue;
        log.info("Final Query <[" + query + "]");
        log.info("Final Query ID is [" + query.getID() + "]");
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