package it.polimi.jasper.engine.sds;

import it.polimi.jasper.engine.windowing.NamedStreamEsperView;
import it.polimi.jasper.engine.querying.RSPQuery;
import it.polimi.jasper.engine.windowing.StreamEsperView;
import it.polimi.jasper.engine.querying.execution.observer.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.yasper.core.enums.EntailmentType;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.SDSBuilder;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.reasoning.Entailment;
import it.polimi.yasper.simple.windowing.TimeVarying;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.riot.system.IRIResolver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Riccardo on 05/09/2017.
 */
@Log4j
@RequiredArgsConstructor
public class JasperSDSBuilder implements SDSBuilder<RSPQuery> {

    @NonNull
    private Map<String, Stream> registeredStreams;
    @NonNull
    private final HashMap<String, Entailment> entailments;
    @NonNull
    protected EngineConfiguration rsp_config;
    @NonNull
    protected QueryConfiguration queryConfiguration;
    @Getter
    protected GenericRuleJenaTVGReasoner reasoner;

    private boolean is_deltas;
    @Getter
    private SDS sds;
    @Getter
    private ContinuousQueryExecution qe;
    private Maintenance maintenance;
    private IRIResolver resolver;
    private RSPQuery query;

    @Override
    public void visit(RSPQuery bq) {

        JenaRDF rdf = new JenaRDF();
        this.query = bq;
        this.query.setConfiguration(queryConfiguration);

        this.resolver = this.query.getResolver();
        this.maintenance = queryConfiguration.getSdsMaintainance();
        this.is_deltas = Maintenance.INCREMENTAL.equals(maintenance);

        if (bq.isRecursive() && !rsp_config.isRecursionEnables()) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }

        String tboxLocation = queryConfiguration.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        EntailmentType entailment = queryConfiguration.getReasoningEntailment();

        log.info("Registering Query [" + bq.getName() + "]");
        log.info(bq.getQ().toString());

        reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailments.get(entailment.name()), tbox);

        MultiUnion defaultgraph = new MultiUnion();
        Graph base = rdf.createGraph().asJenaGraph();
        defaultgraph.addGraph(reasoner.bind(loadStaticGraph(bq, base)));

        JenaSDS jenaSDS = new JenaSDS(defaultgraph, resolver);
        qe = ContinuousQueryExecutionFactory.createObserver(bq, jenaSDS, this.reasoner);
        addNamedStaticGraph(bq, jenaSDS, this.reasoner);

        bq.getWindowMap().forEach((wo, s) -> {
            if (!registeredStreams.containsKey(s.getURI())) {
                throw new UnregisteredStreamExeception(s.getURI());
            } else {
                WindowAssigner<Graph> wa = wo.apply(s);
                wa.setReport(rsp_config.getReport());
                wa.setTick(Tick.TIME_DRIVEN);
                wa.setReportGrain(ReportGrain.SINGLE);
                if (wo.isNamed()) {
                    NamedStreamEsperView n = new NamedStreamEsperView(s.getURI(), this.maintenance, wa);
                    n.addObserver(qe);
                    TimeVarying<InfGraph> tvii = this.reasoner.bindTVG(wa.setView(n));
                    n.setContent(tvii.asT());
                    jenaSDS.add(rdf.createIRI(wo.getName()), tvii);
                } else {
                    StreamEsperView n = new StreamEsperView(this.maintenance, wa);
                    n.addObserver(qe);
                    TimeVarying<InfGraph> tvii = this.reasoner.bindTVG(wa.setView(n));
                    n.setContent(tvii.asT());
                    jenaSDS.add(tvii);
                }
            }
        });

        sds = jenaSDS;
    }

    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public RSPQuery getContinuousQuery() {
        return query;
    }

    @Override
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return qe;
    }

    private void addNamedStaticGraph(RSPQuery bq, JenaSDS sds, GenericRuleJenaTVGReasoner reasoner) {
        //Named Static Graphs
        if (bq.getRSPNamedGraphURIs() != null)
            for (String g : bq.getNamedGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getNamedwindows().keySet(), g)) {
                    Model m = ModelFactory.createDefaultModel().read(g);
                    TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(m.getGraph());
                    sds.addNamedModel(g, new InfModelImpl(bind));
                }
            }
    }

    private Graph loadStaticGraph(RSPQuery bq, Graph gg) {
        Model def = new ModelCom(gg);
        //Default Static GraphItem
        if (bq.getRSPGraphURIs() != null)
            for (String g : bq.getGraphURIs()) {
                log.info(g);
                if (!isWindow(bq.getWindows(), g)) {
                    def = def.read(g);
                }
            }
        return def.getGraph();
    }

    protected boolean isWindow(Set<?> windows, String g) {
        if (windows != null) {
            Iterator<?> iterator = windows.iterator();
            while (iterator.hasNext()) {
                Object next = iterator.next();
                if (next instanceof WindowedStreamNode && ((WindowedStreamNode) next).getStreamURI().equals(g)) {
                    return true;
                } else if (next instanceof Node && ((Node) next).getURI().equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }

}
