package it.polimi.jasper.engine;

import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.JenaTimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.*;
import it.polimi.jasper.engine.stream.GraphStimulus;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.yasper.core.EncodingUtils;
import it.polimi.yasper.core.engine.RSPQLEngine;
import it.polimi.yasper.core.enums.Entailment;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.operators.s2r.DefaultWindow;
import it.polimi.yasper.core.query.operators.s2r.NamedWindow;
import it.polimi.yasper.core.stream.StreamItem;
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
public class JenaRSPQLEngineImpl extends RSPQLEngine {

    public JenaRSPQLEngineImpl(long t0) {
        this.queries = new HashMap<>();
        this.t0 = t0;
        StreamItem typeMap = new GraphStimulus();
        log.info("Added [" + typeMap.getClass() + "] as TStream");
        cepConfig.addEventType("TStream", typeMap);
        cep = EPServiceProviderManager.getProvider(this.getClass().getCanonicalName(), cepConfig);
        cepAdm = cep.getEPAdministrator();
        cepRT = cep.getEPRuntime();

    }

    public ContinuousQueryExecution registerQuery(ContinuousQuery q) {
        return registerQuery((RSPQuery) q, ModelFactory.createDefaultModel(), Maintenance.NAIVE, Entailment.NONE);
    }

    public ContinuousQueryExecution registerQuery(RSPQuery bq, Model tbox, Maintenance maintenance, Entailment entailment) {
        log.info(bq.getQ().toString());

        Model def = loadStaticGraph(bq, new ModelCom(new TimeVaryingGraphBase(-1, null)));

        JenaTVGReasoner reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailment, tbox);

        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(def.getGraph()));

        JenaSDS sds = new JenaSDSImpl(tbox, kb_star, bq.getResolver(), maintenance, "", cep, this);
        ContinuousQueryExecution qe = ContinuousQueryExecutionFactory.create(bq, sds, reasoner);

        addNamedStaticGraph(bq, sds, reasoner);
        addWindows(bq, sds, reasoner);
        addNamedWindows(sds, bq, reasoner);

        sds.addQueryExecutor(bq, qe);

        queries.put(bq, sds);

        return qe;
    }


    private void addWindows(RSPQuery bq, JenaSDS sds, JenaTVGReasoner reasoner) {
        //Default Time-Varying Graph
        int i = 0;
        JenaTimeVaryingInfGraph bind = (JenaTimeVaryingInfGraph) reasoner.bind(new TimeVaryingGraphBase());

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
                String stream_uri = EncodingUtils.encode(w.getStreamURI());
                String window_uri = w.getIri().getURI();
                String statementName = "QUERY" + bq.getId() + "STMT_NDM" + j;

                cepAdm.createEPL(w.getStream().toEPLSchema());

                log.info(w.getStream().toEPLSchema());
                log.info("creating named graph " + window_uri + "");

                JenaTimeVaryingGraph bind = (JenaTimeVaryingGraph) reasoner.bind(new TimeVaryingGraphBase());

                NamedWindow tvg = new NamedWindow(sds.getMaintenanceType(), bind, getEpStatement(sds, w, statementName));

                sds.addNamedTimeVaryingGraph(statementName, window_uri, stream_uri, tvg);//SDS
                sds.addNamedWindowStream(window_uri, stream_uri, new WindowModelCom(bind));//JenaSDS

                j++;
            }
        }
    }

    private EPStatement getEpStatement(JenaSDS sds, Window w, String statementName) {
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

    private void addNamedStaticGraph(RSPQuery bq, JenaSDS sds, JenaTVGReasoner reasoner) {
        //Named Static Graphs
        if (bq.getNamedGraphURIs() != null)
            for (String g : bq.getNamedGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getNamedwindows().keySet(), g)) {
                    Model m = ModelFactory.createDefaultModel().read(g);
                    JenaTimeVaryingInfGraph bind = (JenaTimeVaryingInfGraph) reasoner.bind(m.getGraph());
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