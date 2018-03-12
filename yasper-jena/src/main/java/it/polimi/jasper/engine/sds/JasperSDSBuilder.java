package it.polimi.jasper.engine.sds;

import it.polimi.jasper.engine.querying.execution.observer.ContinuousQueryExecutionFactory;
import it.polimi.jasper.engine.reasoning.GenericRuleJenaTVGReasoner;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.windowing.NamedStreamEsperView;
import it.polimi.jasper.engine.windowing.StreamEsperView;
import it.polimi.yasper.core.enums.EntailmentType;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.SDSBuilder;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.reasoning.Entailment;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.QueryConfiguration;
import it.polimi.yasper.simple.windowing.TimeVarying;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.riot.system.IRIResolver;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Riccardo on 05/09/2017.
 */
@Log4j
@RequiredArgsConstructor
public class JasperSDSBuilder implements SDSBuilder {

    @NonNull
    private Map<String, Stream> registeredStreams;
    @NonNull
    private final HashMap<String, Entailment> entailments;
    @NonNull
    protected EngineConfiguration rsp_config;
    @NonNull
    protected QueryConfiguration queryConfiguration;

    @Nonnull
    private IRIResolver resolver;

    @Getter
    protected GenericRuleJenaTVGReasoner reasoner;

    private boolean is_deltas;
    @Getter
    private SDS sds;
    @Getter
    private ContinuousQueryExecution qe;
    private Maintenance maintenance;

    @Override
    public void visit(ContinuousQuery query) {

        JenaRDF rdf = new JenaRDF();

        this.maintenance = queryConfiguration.getSdsMaintainance();
        this.is_deltas = Maintenance.INCREMENTAL.equals(maintenance);

        if (query.isRecursive() && !rsp_config.isRecursionEnables()) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }

        String tboxLocation = queryConfiguration.getTboxLocation();
        Model tbox = ModelFactory.createDefaultModel().read(tboxLocation);
        EntailmentType entailment = queryConfiguration.getReasoningEntailment();

        this.reasoner = ContinuousQueryExecutionFactory.getGenericRuleReasoner(entailments.get(entailment.name()), tbox);

        MultiUnion defaultgraph = new MultiUnion();

        JenaSDS jenaSDS = new JenaSDS(defaultgraph, resolver);
        qe = ContinuousQueryExecutionFactory.createObserver(query, jenaSDS, this.reasoner);

        query.getNamedGraphURIs().forEach(g -> {
            if (!query.getNamedwindowsURIs().contains(g)) {
                Model m = ModelFactory.createDefaultModel().read(g);
                TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(m.getGraph());
                sds.add(rdf.createIRI(g), bind);
            }
        });

        query.getGraphURIs().forEach(g -> {
            Model m = ModelFactory.createDefaultModel().read(g);
            TimeVaryingInfGraph bind = (TimeVaryingInfGraph) reasoner.bind(m.getGraph());
            sds.add(bind);
        });


        query.getWindowMap().forEach((wo, s) -> {
            if (!registeredStreams.containsKey(resolver.resolveToString("streams/" + s.getURI()))) {
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
    public ContinuousQueryExecution getContinuousQueryExecution() {
        return qe;
    }

}
