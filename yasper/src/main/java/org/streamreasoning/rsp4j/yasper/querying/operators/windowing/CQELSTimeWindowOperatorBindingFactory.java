package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.yasper.content.BindingContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CQELSTimeWindowOperatorBindingFactory implements StreamToRelationOperatorFactory<Graph, Binding> {

    //    private long a;
//    private long t0;
    private final Time time;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;
    private final ContentFactory<Graph, Binding> cf;
//    private ContinuousQueryExecution<Graph, Graph, Triple> context;

    public CQELSTimeWindowOperatorBindingFactory(Time time, Tick tick, Report report, ReportGrain grain) {
//        this.a = a;
//        this.t0 = t0;
        this.time = time;
        this.tick = tick;
        this.report = report;
        this.grain = grain;
//        this.context = context;
        this.cf = new BindingContentFactory();
    }

    //TODO consider a Params interface
    @Override
    public StreamToRelationOp<Graph, Binding> build(long a, long b, long t0) {
        return new CQELSTimeWindowOperatorBinding<>(null, a, time, tick, report, grain, cf);
    }

    public StreamToRelationOp<Graph, Graph> build(Object... parameters) throws InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        //TODO remove iri from constructor
        String opclass = this.getClass().getCanonicalName().replace("Factory", "");
        Class<?> aClass = Class.forName(opclass);
        Constructor[] constructors = aClass.getConstructors();
        return (StreamToRelationOp<Graph, Graph>) constructors[0].newInstance(parameters);
    }


}
