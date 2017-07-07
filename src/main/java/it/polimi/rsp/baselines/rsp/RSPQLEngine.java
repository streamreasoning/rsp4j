package it.polimi.rsp.baselines.rsp;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecutionFactory;
import it.polimi.rsp.baselines.rsp.query.observer.QueryResponseObserver;
import it.polimi.rsp.baselines.rsp.query.reasoning.TVGReasoner;
import it.polimi.rsp.baselines.rsp.query.reasoning.TVGReasonerJena;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.rsp.sds.SDSImpl;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraphBase;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingInfGraph;
import it.polimi.rsp.baselines.rsp.sds.windows.DefaultWindow;
import it.polimi.rsp.baselines.rsp.sds.windows.NamedWindow;
import it.polimi.rsp.baselines.rsp.stream.RSPEsperEngine;
import it.polimi.rsp.baselines.rsp.stream.element.StreamItem;
import it.polimi.sr.rsp.RSPQuery;
import it.polimi.sr.rsp.streams.Window;
import it.polimi.sr.rsp.utils.EncodingUtils;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.ModelCom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Log4j
public abstract class RSPQLEngine extends RSPEsperEngine {

    private Map<Query, SDS> queries;

    public RSPQLEngine(StreamItem eventType, long t0) {
        this.queries = new HashMap<>();
        this.t0 = t0;
        log.info("Added [" + eventType.getClass() + "] as TStream");
        cepConfig.addEventType("TStream", eventType);
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cepConfig);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();

    }

    public ContinuousQueryExecution registerQuery(Query q) {
        return registerQuery((RSPQuery) q, ModelFactory.createDefaultModel(), Maintenance.NAIVE, Entailment.NONE);
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, Model tbox, Maintenance maintenance, Entailment entailment) {
        log.info(bq.getQ().toString());

        Model def = loadStaticGraph(bq, new ModelCom(new TimeVaryingGraphBase(-1, null)));

        TVGReasonerJena reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailment, tbox);

        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(def.getGraph()));

        SDSImpl sds = new SDSImpl(tbox, kb_star, bq.getResolver(), maintenance, "", cep);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.create(bq, sds, reasoner);

        addNamedStaticGraph(bq, sds, reasoner);
        addWindows(bq, sds, reasoner);
        addNamedWindows(sds, bq, reasoner);

        sds.addQueryExecutor(qe);

        queries.put(bq, sds);

        return qe;
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, SDS sds, Entailment e) {
        //TODO check compatibility
        queries.put(bq, sds);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.create(bq, sds, ContinuousQueryExecutionFactory.getGenericRuleReasoner(e, ModelFactory.createDefaultModel()));
        sds.addQueryExecutor(qe);
        return qe;
    }

    public void registerObserver(ContinuousQueryExecution ceq, QueryResponseObserver o) {
        ceq.addObserver(o);
    }

    private void addWindows(RSPQuery bq, SDS sds, TVGReasonerJena reasoner) {
        //Default Time-Varying Graph
        int i = 0;
        TimeVaryingGraph graph = new TimeVaryingGraphBase(-1, null);
        TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(graph);
        bind.rebind();
        sds.asDatasetGraph().setDefaultGraph(bind);

        DefaultWindow defTVG = new DefaultWindow(sds.getMaintenanceType(), bind);
        sds.addTimeVaryingGraph(defTVG);

        if (bq.getWindows() != null) {
            for (Window window : bq.getWindows()) {
                String stream = EncodingUtils.encode(window.getStreamURI());
                String statementName = "QUERY" + "STMT_" + i;
                cepAdm.createEPL(window.getStream().toEPLSchema());
                log.info(window.getStream().toEPLSchema());
                EPStatement epl = getEpStatement(sds, window, statementName);
                epl.addListener(defTVG);
                sds.addDefaultWindowStream(statementName, stream);
                i++;
            }
        }
    }

    private void addNamedWindows(SDS sds, RSPQuery bq, TVGReasonerJena reasoner) {
        int j = 0;
        if (bq.getNamedwindows() != null) {
            for (Map.Entry<Node, Window> entry : bq.getNamedwindows().entrySet()) {
                Window w = entry.getValue();
                String stream_uri = EncodingUtils.encode(w.getStreamURI());
                String window_uri = w.getIri().getURI();
                String statementName = "QUERY" + bq.getId() + "STMT_NDM" + j;

                cepAdm.createEPL(w.getStream().toEPLSchema());

                log.info(w.getStream().toEPLSchema());
                log.info("creating named graph " + window_uri + "");

                EPStatement epl = getEpStatement(sds, w, statementName);
                log.info(epl.toString());

                TimeVaryingGraph bind = (TimeVaryingGraph) reasoner.bind(new TimeVaryingGraphBase());
                NamedWindow tvg = new NamedWindow(sds.getMaintenanceType(), bind, epl);
                sds.addNamedTimeVaryingGraph(statementName, window_uri, stream_uri, tvg);
                epl.addListener(tvg);
                j++;
            }
        }
    }

    private EPStatement getEpStatement(SDS sds, Window w, String statementName) {
        EPStatement epl;
        if (Maintenance.INCREMENTAL.equals(sds.getMaintenanceType())) {
            epl = cepAdm.create(w.toIREPL(), statementName);
            log.info(w.toIREPL().toEPL());
        } else {
            epl = cepAdm.create(w.toEPL(), statementName);
            log.info(w.toEPL().toEPL());
        }
        return epl;
    }

    private void addNamedStaticGraph(RSPQuery bq, SDS sds, TVGReasonerJena reasoner) {
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

    private boolean isWindow(Set<?> windows, String g) {
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

    public SDS getSDS(Query q) {
        return queries.get(q);
    }


}