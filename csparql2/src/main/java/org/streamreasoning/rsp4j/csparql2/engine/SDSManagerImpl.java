package org.streamreasoning.rsp4j.csparql2.engine;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Graph;
import org.apache.jena.irix.IRIs;
import org.apache.jena.mem.GraphMem;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.graph.GraphFactory;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.StreamOperator;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.exceptions.StreamRegistrationException;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.csparql2.operators.EsperGGWindowOperator;
import org.streamreasoning.rsp4j.csparql2.operators.R2ROperatorSPARQL;
import org.streamreasoning.rsp4j.csparql2.operators.R2ROperatorSPARQLEnt;
import org.streamreasoning.rsp4j.csparql2.syntax.RSPQLJenaQuery;
import org.streamreasoning.rsp4j.esper.engine.esper.StreamRegistrationService;
import org.streamreasoning.rsp4j.esper.querying.Entailment;
import org.streamreasoning.rsp4j.esper.sds.SDSImpl;
import org.streamreasoning.rsp4j.esper.sds.tv.TimeVaryingStatic;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 05/09/2017.
 */
@Log4j
public class SDSManagerImpl {

    private final RSPQLJenaQuery query;

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
    private final CSPARQLEngine CSPARQLEngine;

    @Getter
    protected static Reasoner reasoner;

    @Getter
    private SDSImpl<Graph> sds;

    @Getter
    private JenaContinuousQueryExecution cqe;

    private Maintenance maintenance;

    private String tboxLocation;
    private DataStream<Graph> out;

    public SDSManagerImpl(CSPARQLEngine CSPARQLEngine, RSPQLJenaQuery query, Time time, Report report, String responseFormat, Boolean enabled_recursion, Boolean usingEventTime, ReportGrain reportGrain, Tick tick, StreamRegistrationService stream_registration_service, Maintenance sdsMaintainance, String tboxLocation, Entailment et) {
        this.CSPARQLEngine = CSPARQLEngine;
        this.query = query;
        this.time = time;
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
                Graph staticGraph = GraphFactory.createGraphMem();
                // load static data
                StmtIterator stmtIt = m.listStatements();
                while (stmtIt.hasNext()) {
                    staticGraph.add(stmtIt.nextStatement().asTriple());
                }
                this.sds.add(new TimeVaryingStatic<>(sds, staticGraph));
            }
        });
        // Remove graph and name graph definitions from query after loading
        query.getGraphURIs().clear();
        query.getNamedGraphURIs().clear();

        Map<String, DataStream<Graph>> registeredStreams = stream_registration_service.getRegisteredStreams();

        DataStream outputStream = query.getOutputStream();

        if (outputStream != null)
            this.out = CSPARQLEngine.register(outputStream);

        List<StreamToRelationOp<Graph, Graph>> windows = query.getWindowMap().entrySet().stream().map(e -> {

            WindowNode wo = e.getKey();
            DataStream s = e.getValue();

            String key = IRIs.resolve(s.getName());
            if (!registeredStreams.containsKey(s.getName())) {
                throw new StreamRegistrationException(s.getName());
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

        RelationToRelationOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> r2r = reasoner != null ?
                new R2ROperatorSPARQLEnt(query, reasoner, sds, IRIs.getBaseStr()) :
                new R2ROperatorSPARQL(query, sds, IRIs.getBaseStr());

        RelationToStreamOperator<SolutionMapping<Binding>, SolutionMapping<Binding>> s2r = ContinuousQueryExecutionFactory.getToStreamOperator(r2S);

        this.sds.addObserver(this.cqe = new JenaContinuousQueryExecution(out, query, sds, r2r, s2r, windows));

        return sds;

    }

    public ContinuousQueryExecution<Graph, Graph, SolutionMapping<Binding>, SolutionMapping<Binding>> getContinuousQueryExecution() {
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


    public Graph graph() {
        return reasoner != null ? reasoner.bind(new GraphMem()) : new GraphMem();
    }

}
