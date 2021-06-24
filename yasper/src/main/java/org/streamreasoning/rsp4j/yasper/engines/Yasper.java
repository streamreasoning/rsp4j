package org.streamreasoning.rsp4j.yasper.engines;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.engine.features.QueryRegistrationFeature;
import org.streamreasoning.rsp4j.api.engine.features.StreamRegistrationFeature;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionImpl;
import org.streamreasoning.rsp4j.yasper.content.BindingContentFactory;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.examples.StreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.syntax.RSPQL;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.streamreasoning.rsp4j.api.RDFUtils.createIRI;


public class Yasper implements QueryRegistrationFeature<RSPQL>, StreamRegistrationFeature<RDFStream, RDFStream> {

    private final long t0;
    private final String baseUri;
    //    private final String windowOperatorFactory;
    private final String S2RFactory = "yasper.window_operator_factory";
    private final StreamToRelationOperatorFactory<Graph, Graph> wf;

    private final Time time;
    protected EngineConfiguration rsp_config;
    protected Map<String, SDS> assignedSDS;
    protected Map<String, ContinuousQueryExecution> queryExecutions;
    protected Map<String, ContinuousQuery> registeredQueries;
    protected Map<String, List<QueryResultFormatter>> queryObservers;
    protected Set<DataStream<Graph>> registeredStreams;
    private ContentFactory cf;
    private Report report;
    private Tick tick;
    private ReportGrain report_grain;


    public Yasper(EngineConfiguration rsp_config) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.rsp_config = rsp_config;
        this.report = rsp_config.getReport();
        this.baseUri = rsp_config.getBaseIRI();
        this.report_grain = rsp_config.getReportGrain();
        this.tick = rsp_config.getTick();
        this.t0 = rsp_config.gett0();
        this.assignedSDS = new HashMap<>();
        this.registeredQueries = new HashMap<>();
        this.registeredStreams = new HashSet<>();
        this.queryObservers = new HashMap<>();
        this.queryExecutions = new HashMap<>();
        this.time = new TimeImpl(0);


        switch (rsp_config.getContentFormat()) {
            case BINDING:
                cf = new BindingContentFactory(time);
            case GRAPH:
                cf = new GraphContentFactory(time);
            default:
                cf = new GraphContentFactory(time);
        }

        Class<?> aClass = Class.forName(rsp_config.getString(S2RFactory));

        this.wf = (StreamToRelationOperatorFactory<Graph, Graph>) aClass
                .getConstructor(
                        Time.class,
                        Tick.class,
                        Report.class,
                        ReportGrain.class,
                        ContentFactory.class)
                .newInstance(
                        time,
                        tick,
                        report,
                        report_grain,
                        cf);

    }

    @Override
    public ContinuousQueryExecution<Graph, Graph, Binding, Binding> register(RSPQL q) {
//        return new ContinuousQueryExecutionFactoryImpl(q, windowOperatorFactory, registeredStreams, report, report_grain, tick, t0).build();

        SDS<Graph> sds = new SDSImpl();

        DataStream<Binding> out = new StreamImpl<Binding>(q.getID());

        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = new ContinuousQueryExecutionImpl<Graph, Graph, Binding, Binding>(sds, q, out, q.r2r(), q.r2s());

        Map<? extends WindowNode, DataStream<Graph>> windowMap = q.getWindowMap();

        windowMap.forEach((WindowNode wo, DataStream<Graph> s) -> {
            if (registeredStreams.contains(s)) {
                //TODO switch to parametric method WindowNode.params() for the simple ones
                //TODO for the BGP aware windows, we need to extract bgp from R2R and push them to the window, therefore we need a way to visualize the r2r tree
                StreamToRelationOp<Graph, Graph> build = wf.build(wo.getRange(), wo.getStep(), wo.getT0());
                StreamToRelationOp<Graph, Graph> wop = build.link(cqe);
                TimeVarying<Graph> tvg = wop.apply(s);
                if (wo.named()) {
                    if (wo.named()) {
                        sds.add(createIRI(wo.iri()), tvg);
                    } else {
                        sds.add(tvg);
                    }
                }
            }
        });

        return cqe;
    }

    public Time time() {
        return time;
    }

    @Override
    public RDFStream register(RDFStream s) {
        registeredStreams.add(s);
        return s;
    }
}
