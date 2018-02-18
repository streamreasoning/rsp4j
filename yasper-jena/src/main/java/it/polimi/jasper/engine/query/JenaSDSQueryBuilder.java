package it.polimi.jasper.engine.query;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPServiceProvider;
import it.polimi.jasper.engine.instantaneous.GraphBase;
import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.query.execution.observer.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import it.polimi.jasper.engine.reasoning.JenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.spe.report.ReportGrain;
import it.polimi.spe.scope.Tick;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.engine.Entailment;
import it.polimi.yasper.core.enums.EntailmentType;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.rdf.model.impl.ModelCom;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.riot.system.IRIResolver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by riccardo on 05/09/2017.
 */
@Log4j
@RequiredArgsConstructor
public class JenaSDSQueryBuilder implements SDSBuilder<RSPQuery> {

    private final HashMap<String, Entailment> entailments;
    @NonNull
    protected EngineConfiguration rsp_config;
    @NonNull
    protected QueryConfiguration queryConfiguration;
    @Getter
    protected JenaTVGReasoner reasoner;
    @NonNull
    private EPAdministrator cepAdm;
    @NonNull
    private EPServiceProvider cep;
    @NonNull
    private Map<String, Stream> registeredStreams;
    private boolean is_deltas;
    @Getter
    private SDS sds;
    @Getter
    private ContinuousQueryExecution qe;
    private Maintenance maintenance;
    private IRIResolver resolver;
    private RSPQuery query;

    private DefaultTVG defaultTVG;
    private List<NamedTVG> namedWOFS;

    public JenaSDSQueryBuilder(EPAdministrator cepAdm, EPServiceProvider cep, Map<String, Stream> registeredStreams, HashMap<String, Entailment> entailments, EngineConfiguration rsp_config, QueryConfiguration c) {
        this.cepAdm = cepAdm;
        this.cep = cep;
        this.registeredStreams = registeredStreams;
        this.rsp_config = rsp_config;
        this.queryConfiguration = c;
        namedWOFS = new ArrayList<>();
        this.entailments = entailments;
    }

    @Override
    public void visit(RSPQuery bq) {
        this.query = bq;
        this.query.setConfiguration(queryConfiguration);
        String tboxLocation = queryConfiguration.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        this.maintenance = queryConfiguration.getSdsMaintainance();
        EntailmentType entailment = queryConfiguration.getReasoningEntailment();
        boolean recursionEnabled = rsp_config.isRecursionEnables();

        log.info("Registering Query [" + bq.getName() + "]");
        log.info(bq.getQ().toString());

        is_deltas = Maintenance.INCREMENTAL.equals(maintenance);

        if (bq.isRecursive() && !recursionEnabled) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }

        reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailments.get(entailment.name()), tbox);

        GraphBase base = new GraphBase();
        Model m = loadStaticGraph(bq, new ModelCom(base));
        InfModel kb_star = ModelFactory.createInfModel(reasoner.bind(m.getGraph()));
        defaultTVG = new DefaultTVG(base);

        //SET DEFAULT STREAM
        this.resolver = bq.getResolver();


        JenaSDS jenaSDS = new JenaSDS(tbox, kb_star, defaultTVG, resolver, maintenance, cep, this.reasoner, rsp_config.partialWindowsEnabled());

        addNamedStaticGraph(bq, jenaSDS, this.reasoner);

        List<TimeVarying> collect = bq.getWindowMap().entrySet().stream()
                .map(e -> {
                    WindowOperator key = e.getKey();
                    String uri = e.getValue().getURI();
                    Stream rdfStream = registeredStreams.get(uri);
                    return apply(key, true, rdfStream);
                })
                .collect(Collectors.toList());

        //SET ALL NAMED MODELS
        this.namedWOFS.forEach(namedTVG -> {
            String uri = resolver.resolveToString(namedTVG.getUri());
            JenaGraph g = namedTVG.getContent();
            InstantaneousInfGraph bind = reasoner.bind(g);
            jenaSDS.addNamedModel(uri, new InfModelImpl(bind));
            namedTVG.setContent(bind);
        });


        sds = jenaSDS;
        qe = ContinuousQueryExecutionFactory.createObserver(bq, sds, this.reasoner);
        collect.forEach(tvi -> qe.add(tvi));


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

    private void addNamedStaticGraph(RSPQuery bq, JenaSDS sds, Reasoner reasoner) {
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

    private Model loadStaticGraph(RSPQuery bq, Model def) {
        //Default Static Graph
        if (bq.getRSPGraphURIs() != null)
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
                if (next instanceof WindowedStreamNode && ((WindowedStreamNode) next).getStreamURI().equals(g)) {
                    return true;
                } else if (next instanceof Node && ((Node) next).getURI().equals(g)) {
                    return true;
                }
            }
        }
        return false;
    }

    private TimeVarying apply(WindowOperator wo, boolean named, Stream s) {

        WindowAssigner wa = wo.apply(s);

        //TODO add the reporting strategy of the engine
        // wa.addReportingStrategy(null);
        // OR TODO
        wa.setReport(rsp_config.getReport());

        //TODO set the tick, which is a system parameter
        wa.setTick(Tick.TIME_DRIVEN);
        wa.setReportGrain(ReportGrain.SINGLE);

        //TODO attach the listener that will materialize the content and notify the SDS to compute
        if (named) {
            NamedTVG n = new NamedTVG(s.getURI(), this.maintenance, wa);
            wa.setView(n);
            namedWOFS.add(n);
            return n;
        } else {
            wa.setView(defaultTVG);
            //defaultTVG.setWindowOperator(wo);
            return defaultTVG;
        }
    }

}
