package it.polimi.jasper.engine;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.*;
import it.polimi.jasper.engine.stream.GraphStimulus;
import it.polimi.jasper.parser.RSPQLParser;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.engine.RSPQLEngine;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredQueryExeception;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.exceptions.UnsuportedQueryClassExecption;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.operators.s2r.DefaultWindow;
import it.polimi.yasper.core.query.operators.s2r.NamedWindow;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.EncodingUtils;
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

    public JenaRSPQLEngineImpl(long t0) {
        this.t0 = t0;
        StreamItem typeMap = new GraphStimulus();
        log.info("Added [" + typeMap.getClass() + "] as TStream");
        cepConfig.addEventType("TStream", typeMap);
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cepConfig);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();
    }

    @Override
    public void registerStream(Stream s) {
        log.info("Registering Stream [" + s.getURI() + "]");
        s.setRSPEngine(this);
        cepAdm.createEPL(s.toEPLSchema(), EncodingUtils.encode(s.getURI()));
        registeredStreams.put(EncodingUtils.encode(s.getURI()), s);
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
        Model tbox = ModelFactory.createDefaultModel().read(c.getTboxLocation());
        Maintenance maintenance = c.getSdsMaintainance();
        Entailment entailment = c.getReasoningEntailment();
        if ("it.polimi.jasper.engine.query.RSPQuery".equals(c.getQueryClass())) {
            return registerQuery((RSPQuery) q, tbox, maintenance, entailment);
        } else {
            throw new UnsuportedQueryClassExecption();
        }
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, Model tbox, Maintenance maintenance, Entailment entailment) {
        log.info("Registering Query [" + bq.getName() + "]");

        registeredQueries.put(bq.getName(), bq);
        queryObservers.put(bq.getName(), new ArrayList<QueryResponseFormatter>());

        log.info(bq.getQ().toString());

        Model def = loadStaticGraph(bq, new ModelCom(new TimeVaryingGraphBase()));

        JenaTVGReasoner reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailment, tbox);

        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(def.getGraph()));

        JenaSDS sds = new JenaSDSImpl(tbox, kb_star, bq.getResolver(), maintenance, "", cep, this);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.create(bq, sds, reasoner);

        sds.addQueryExecutor(bq, qe);

        addNamedStaticGraph(bq, sds, reasoner);
        addWindows(bq, sds, reasoner);
        addNamedWindows(sds, bq, reasoner);

        assignedSDS.put(bq.getName(), sds);
        registeredQueries.put(bq.getName(), bq);
        queryExecutions.put(bq.getName(), qe);

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
            SDS sds = assignedSDS.remove(query);
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
        log.info("Final Query ID is [" + query.getID() + "]");
        return query;
    }

    private void addWindows(RSPQuery bq, JenaSDS sds, JenaTVGReasoner reasoner) {
        //Default Time-Varying Graph
        int i = 0;
        TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(new TimeVaryingGraphBase());

        sds.addDefaultWindow(new WindowModelCom(bind)); //JenaSDS

        DefaultWindow defTVG = new DefaultWindow(sds.getMaintenanceType(), bind);

        sds.addTimeVaryingGraph(defTVG);

        if (bq.getWindows() != null) {
            for (Window window : bq.getWindows()) {
                String stream = EncodingUtils.encode(window.getStreamURI());
                String statementName = "QUERY" + "STMT_" + i;
                cepAdm.createEPL(window.getStream().toEPLSchema());
                log.info(window.getStream().toEPLSchema());

                defTVG.addStatement(getEpStatement(sds, window, statementName));

                sds.addDefaultWindowStream(statementName, stream);
                i++;
            }
        }
    }

    private void addNamedWindows(JenaSDS sds, RSPQuery bq, JenaTVGReasoner reasoner) {
        int j = 0;
        if (bq.getNamedwindows() != null) {
            for (Map.Entry<Node, Window> entry : bq.getNamedwindows().entrySet()) {
                Window w = entry.getValue();
                String epl_stream_uri = EncodingUtils.encode(w.getStreamURI());

                EPStatement stream_schema = cepAdm.getStatement(epl_stream_uri);
                if (stream_schema == null) {
                    throw new UnregisteredStreamExeception(w.getStreamURI());
                }
                // cepAdm.createEPL(w.getStream().toEPLSchema(), epl_stream_uri);

                String window_uri = w.getIri().getURI();
                String epl_statement_name = "QUERY" + bq.getName() + "STMT_NDM" + j;


                log.info(w.getStream().toEPLSchema());
                log.info("creating named graph " + window_uri + "");

                TimeVaryingGraph bind = (TimeVaryingGraph) reasoner.bind(new TimeVaryingGraphBase());

                NamedWindow tvg = new NamedWindow(sds.getMaintenanceType(), bind, getEpStatement(sds, w, epl_statement_name));

                sds.addNamedTimeVaryingGraph(epl_statement_name, window_uri, epl_stream_uri, tvg);//SDS
                sds.addNamedWindowStream(window_uri, epl_stream_uri, new WindowModelCom(bind));//JenaSDS

                j++;
            }
        }
    }

    private EPStatement getEpStatement(JenaSDS sds, Window w, String epl_statement_name) {
        EPStatement epl;
        if (Maintenance.INCREMENTAL.equals(sds.getMaintenanceType())) {
            epl = cepAdm.create(w.toIREPL(), epl_statement_name);
            log.info(w.toIREPL().toEPL());
        } else {
            epl = cepAdm.create(w.toEPL(), epl_statement_name);
            log.info(w.toEPL().toEPL());
        }
        return epl;
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