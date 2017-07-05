package it.polimi.rsp.baselines.rsp;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import it.polimi.heaven.rsp.rsp.querying.Query;
import it.polimi.rsp.baselines.enums.Entailment;
import it.polimi.rsp.baselines.enums.Maintenance;
import it.polimi.rsp.baselines.exceptions.StreamRegistrationException;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecution;
import it.polimi.rsp.baselines.rsp.query.execution.ContinuousQueryExecutionFactory;
import it.polimi.rsp.baselines.rsp.query.observer.QueryResponseObserver;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.rsp.sds.graphs.DefaultTVG;
import it.polimi.rsp.baselines.rsp.sds.graphs.NamedTVG;
import it.polimi.rsp.baselines.rsp.sds.SDSImpl;
import it.polimi.rsp.baselines.rsp.stream.RSPEsperEngine;
import it.polimi.rsp.baselines.rsp.stream.element.StreamItem;
import it.polimi.sr.rsp.RSPQuery;
import it.polimi.sr.rsp.streams.Window;
import it.polimi.sr.rsp.utils.EncodingUtils;
import it.polimi.streaming.Stimulus;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.util.*;

@Log4j
public abstract class RSPQLEngine extends RSPEsperEngine {

    private Map<Query, SDS> queries;
    protected long t0;

    public RSPQLEngine(StreamItem eventType, long t0) {

        this.queries = new HashMap<>();
        this.t0 = t0;

        log.info("Added [" + eventType.getClass() + "] as TStream");
        cepConfig.addEventType("TStream", eventType);
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cepConfig);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();

    }

    public void startProcessing() {
        cepRT.sendEvent(new CurrentTimeEvent(t0));
    }

    public void stopProcessing() {
        log.info("Engine is closing");
        // stop the CEP engine
        for (String stmtName : cepAdm.getStatementNames()) {
            EPStatement stmt = cepAdm.getStatement(stmtName);
            if (!stmt.isStopped()) {
                stmt.stop();
            }
        }
    }

    public ContinuousQueryExecution registerQuery(Query q) {
        return registerQuery((RSPQuery) q, Maintenance.NAIVE, Entailment.NONE);
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, Maintenance maintenance, Entailment ontology_language) {
        log.info(bq.getQ().toString());
        Model def = ModelFactory.createMemModelMaker().createDefaultModel();
        Model tbox = ModelFactory.createMemModelMaker().createDefaultModel();

        SDSImpl sds = new SDSImpl(tbox, def, bq.getResolver(), maintenance, ontology_language, "");

        initializeSDS(bq, sds, def);

        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.ccreate(bq, sds, ontology_language);
        qe.bindTbox(tbox);

        sds.addQueryExecutor(qe);
        return qe;
    }

    private void initializeSDS(RSPQuery bq, SDS sds, Model def) {
        queries.put(bq, sds);

        //Default Static Graph
        if (bq.getGraphURIs() != null)
            for (String g : bq.getGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getWindows(), g)) {
                    def = def.read(g);
                }
            }

        sds.rebind(def);

        //Named Static Graphs
        if (bq.getNamedGraphURIs() != null)
            for (String g : bq.getNamedGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getNamedwindows().keySet(), g)) {
                    Model m = ModelFactory.createDefaultModel().read(g);
                    m = sds.rebind(m);
                    sds.addNamedModel(g, m);
                }
            }

        //Default Time-Varying Graph
        int i = 0;
        DefaultTVG defTVG = new DefaultTVG(sds.getMaintenanceType(), sds.getDefaultModel().getGraph(), Collections.EMPTY_SET);
        sds.addTimeVaryingGraph(defTVG);

        if (bq.getWindows() != null) {
            for (Window window : bq.getWindows()) {
                log.info(window.getStream().toEPLSchema());
                cepAdm.createEPL(window.getStream().toEPLSchema());
                String stream = EncodingUtils.encode(window.getStreamURI());

                if (!sds.addDefaultWindowStream(stream)) {
                    throw new StreamRegistrationException("Impossible to register stream [" + stream + "]");
                }
                String statementName = "QUERY" + "STMT_" + i;
                EPStatementObjectModel sodaStatement;
                if (sds.getMaintenanceType().equals(Maintenance.INCREMENTAL)) {
                    sodaStatement = window.toIREPL();
                } else {
                    sodaStatement = window.toEPL();
                }
                log.info(sodaStatement.toEPL());

                EPStatement epl = cepAdm.create(sodaStatement, statementName);
                epl.addListener(defTVG);
                sds.addStatementName(statementName);

                i++;
            }
        }

        // Named Time-Varying Graph

        int j = 0;


        if (bq.getNamedwindows() != null) {
            for (Map.Entry<Node, Window> entry : bq.getNamedwindows().entrySet()) {
                Window w = entry.getValue();
                String stream = EncodingUtils.encode(w.getStreamURI());
                String window = w.getIri().getURI();
                log.info(w.getStream().toEPLSchema());
                cepAdm.createEPL(w.getStream().toEPLSchema());
                log.info("creating named graph " + window + "");

                String statementName = "QUERY" + bq.getId() + "STMT_NDM" + j;

                EPStatementObjectModel sodaStatement;
                if (sds.getMaintenanceType().equals(Maintenance.INCREMENTAL)) {
                    sodaStatement = w.toIREPL();
                } else {
                    sodaStatement = w.toEPL();
                }
                log.info(sodaStatement.toEPL());


                EPStatement epl = cepAdm.create(sodaStatement, statementName);
                NamedTVG tvg = new NamedTVG(sds.getMaintenanceType(), epl);

                Model bind = sds.bind(tvg.getGraph());
                tvg.setGraph(bind.getGraph());
                if (!sds.addNamedWindowStream(window, stream, bind)) {
                    throw new StreamRegistrationException(
                            "Impossible to register window named  [" + window + "] on stream [" + stream + "]");
                }

                sds.addNamedTimeVaryingGraph(window, tvg);

                epl.addListener(tvg);
                sds.addStatementName(statementName);
                j++;
            }
        }
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

    public void registerObserver(ContinuousQueryExecution ceq, QueryResponseObserver o) {
        ceq.addObserver(o);
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, SDS sds, Entailment e) {
        //TODO check compatibility
        queries.put(bq, sds);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.ccreate(bq, sds, e);
        sds.addQueryExecutor(qe);
        return qe;
    }

    public boolean process(Stimulus e) {
        log.info("Current runtime is  [" + cepRT.getCurrentTime() + "]");
        this.currentEvent = e;
        StreamItem g = (StreamItem) e;
        if (cepRT.getCurrentTime() < g.getAppTimestamp()) {
            log.info("Sent time event with current [" + (g.getAppTimestamp()) + "]");
            cepRT.sendEvent(new CurrentTimeEvent(g.getAppTimestamp()));
            currentTimestamp = g.getAppTimestamp();// TODO
            log.info("Current runtime is now [" + cepRT.getCurrentTime() + "]");
        }
        cepRT.sendEvent(g, EncodingUtils.encode(g.getStreamURI()));


        log.info("Received Stimulus [" + g + "]");
        rspEventsNumber++;
        log.info("Current runtime is  [" + this.cepRT.getCurrentTime() + "]");
        return true;
    }
}