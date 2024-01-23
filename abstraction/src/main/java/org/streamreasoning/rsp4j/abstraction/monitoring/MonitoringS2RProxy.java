package org.streamreasoning.rsp4j.abstraction.monitoring;

import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.List;

public class MonitoringS2RProxy<I,W> implements StreamToRelationOp<I,W> {

    private final String name;
    private final StreamToRelationOp<I,W> s2r;

    public MonitoringS2RProxy(StreamToRelationOp<I,W> s2r, String name){
        this.s2r = s2r;
        this.name = name;
    }
    public MonitoringS2RProxy(StreamToRelationOp<I,W> s2r){
        this(s2r,s2r.getClass().getSimpleName());
    }
    @Override
    public Report report() {
        return s2r.report();
    }

    @Override
    public Tick tick() {
        return s2r.tick();
    }

    @Override
    public Time time() {
    return s2r.time();
    }

    @Override
    public ReportGrain grain() {
        return s2r.grain();
    }

    @Override
    public Content<I, W> content(long now) {
        long t0 = System.currentTimeMillis();
        Content<I, W> result = s2r.content(now);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"content",resultTime);
        return result;
    }

    @Override
    public List<Content<I, W>> getContents(long now) {
        long t0 = System.currentTimeMillis();
        List<Content<I, W>> result = s2r.getContents(now);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"getContents",resultTime);
        return result;
    }

    @Override
    public TimeVarying<W> get() {
        return s2r.get();
    }

    @Override
    public String iri() {
        return s2r.iri();
    }

    @Override
    public Content<I, W> compute(long t_e, Window w) {
        long t0 = System.currentTimeMillis();
        Content<I, W> result = s2r.compute(t_e, w);
        long resultTime = System.currentTimeMillis() - t0;
        RSP4JMonitor.reportEvalTime(this.name,"compute",resultTime);
        return result;
    }

    @Override
    public StreamToRelationOp<I, W> link(ContinuousQueryExecution<I, W, ?, ?> context) {
        return s2r.link(context);
    }

    @Override
    public TimeVarying<W> apply(DataStream<I> s) {
        return s2r.apply(s);
    }

    @Override
    public void notify(I arg, long ts) {
        s2r.notify(arg,ts);
    }
}
