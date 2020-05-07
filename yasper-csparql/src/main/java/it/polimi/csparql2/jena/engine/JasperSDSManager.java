package it.polimi.csparql2.jena.engine;

import it.polimi.csparql2.jena.operators.EsperGGWindowOperator;
import it.polimi.csparql2.jena.operators.R2ROperatorSPARQL;
import it.polimi.csparql2.jena.operators.R2ROperatorSPARQLEnt;
import it.polimi.jasper.engine.esper.StreamRegistrationService;
import it.polimi.csparql2.jena.syntax.RSPQLJenaQuery;
import it.polimi.jasper.querying.Entailment;
import it.polimi.jasper.sds.SDSImpl;
import it.polimi.jasper.sds.tv.TimeVaryingStatic;
import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.exceptions.StreamRegistrationException;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.operators.s2r.syntax.WindowNode;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.SDSManager;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.graph.GraphFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 05/09/2017.
 */
@Log4j
public class JasperSDSManager implements SDSManager {

    private final RSPQLJenaQuery query;

    private final IRIResolver resolver;

    private final Report report;
    private final String responseFormat;
    private final Boolean distinct = true;
    private final Boolean enabled_recursion;
    private final Boolean usingEventTime;
    private final ReportGrain reportGrain;
    private final Tick tick;

    private final StreamRegistrationService<Graph> stream_registration_service;
    private final Entailment et;
    private final Time time;
    private final Jasper jasper;

    @Getter
    protected Reasoner reasoner;

    @Getter
    private SDSImpl<Graph> sds;

    @Getter
    private JenaContinuousQueryExecution cqe;

    private Maintenance maintenance;

    private String tboxLocation;
    private DataStreamImpl<Graph> out;

    public JasperSDSManager(Jasper jasper, RSPQLJenaQuery query, Time time, String baseUri, Report report, String responseFormat, Boolean enabled_recursion, Boolean usingEventTime, ReportGrain reportGrain, Tick tick, StreamRegistrationService stream_registration_service, Maintenance sdsMaintainance, String tboxLocation, Entailment et) {
        this.jasper = jasper;
        this.query = query;
        this.time = time;
        this.resolver = IRIResolver.create(baseUri);
        this.report = report;
        this.responseFormat = responseFormat;
        this.enabled_recursion = enabled_recursion;
        this.usingEventTime = usingEventTime;
        this.reportGrain = reportGrain;
        this.tick = tick;
        this.stream_registration_service = stream_registration_service;
        this.maintenance = sdsMaintainance;
        this.tboxLocation = tboxLocation;
        this.et = et;
    }

    @Override
    public SDS build() {

        getReasoner(et, tboxLocation);

        if (query.isRecursive() && !this.enabled_recursion) {
            throw new UnsupportedOperationException("Recursion must be enabled");
        }

        this.sds = new SDSImpl<>();

        //Load Static Knowledge
        query.getNamedGraphURIs().forEach(iri -> {
            Model m = ModelFactory.createDefaultModel();
            if (!query.getNamedwindowsURIs().contains(iri)) {
                if (this.reasoner != null) {
                    InfGraph infGraph = new InfModelImpl(this.reasoner.bind(m.read(iri).getGraph())).getInfGraph();
                    this.sds.add(RDFUtils.createIRI(iri), new TimeVaryingStatic<>(sds, infGraph, iri));
                } else {
                    this.sds.add(RDFUtils.createIRI(iri), new TimeVaryingStatic<>(sds, GraphFactory.createGraphMem(), iri));
                }
            }
        });

        query.getGraphURIs().forEach(g -> {
            Model m = ModelFactory.createDefaultModel().read(g);
            if (this.reasoner != null) {
                InfGraph infGraph = new InfModelImpl(this.reasoner.bind(m.read(g).getGraph())).getInfGraph();
                this.sds.add(new TimeVaryingStatic<>(sds, infGraph));
            } else {
                this.sds.add(new TimeVaryingStatic<>(sds, GraphFactory.createGraphMem()));
            }
        });

        Map<String, WebDataStream<Graph>> registeredStreams = stream_registration_service.getRegisteredStreams();

        WebStream outputStream = query.getOutputStream();

        this.out = jasper.register(outputStream);

        List<StreamToRelationOperator<Graph, Graph>> windows = query.getWindowMap().entrySet().stream().map(e -> {

            WindowNode wo = e.getKey();
            WebStream s = e.getValue();

            String key = this.resolver.resolveToString(s.getURI());
            if (!registeredStreams.containsKey(s.getURI())) {
                throw new StreamRegistrationException(s.getURI());
            } else {

                EsperGGWindowOperator ewo = new EsperGGWindowOperator(
                        this.tick,
                        this.report,
                        this.usingEventTime,
                        this.reportGrain,
                        this.maintenance,
                        this.time,
                        wo,
                        sds);

                if (wo.named())
                    this.sds.add(RDFUtils.createIRI(wo.iri()), ewo.apply(registeredStreams.get(key)));
                else
                    this.sds.add(ewo.apply(registeredStreams.get(key)));
                return ewo;

            }
        }).collect(Collectors.toList());


        StreamOperator r2S = query.getR2S() != null ? query.getR2S() : StreamOperator.RSTREAM;

        RelationToRelationOperator<Binding> r2r = reasoner != null ?
                new R2ROperatorSPARQLEnt(query, reasoner, sds, resolver.getBaseIRIasString()) :
                new R2ROperatorSPARQL(query, sds, resolver.getBaseIRIasString());

        RelationToStreamOperator<Binding> s2r = ContinuousQueryExecutionFactory.getToStreamOperator(r2S);

        this.sds.addObserver(this.cqe = new JenaContinuousQueryExecution(resolver, out, query, sds, r2r, s2r, windows));

        return sds;

    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding> getContinuousQueryExecution() {
        return cqe;
    }

    public Reasoner getReasoner(Entailment et, String tboxLocation) {
        switch (et) {
            case OWL:
                reasoner = ReasonerRegistry.getOWLReasoner().bindSchema(ModelFactory.createDefaultModel().read(tboxLocation));
            case RDFS:
                reasoner = ReasonerRegistry.getRDFSReasoner().bindSchema(ModelFactory.createDefaultModel().read(tboxLocation));
            case OWL2RL:
                reasoner = ReasonerRegistry.getRDFSReasoner().bindSchema(ModelFactory.createDefaultModel().read(tboxLocation));
            case NONE:
            default:
                return reasoner;
        }
    }

}
