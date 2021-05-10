package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.exceptions.OutOfOrderElementException;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.ObservableStreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;

import java.util.*;
import java.util.function.Consumer;

@Log4j
public class CSPARQLStreamToRelationOpSingle<I extends EventBean> extends ObservableStreamToRelationOp<I, Collection<I>> {

    private Scope<I,Collection<I>> scope;
    private final WindowBuffer<I> windowBuffer;

    public CSPARQLStreamToRelationOpSingle(IRI iri, long size, long b, Time instance, Tick tick, Report report, ReportGrain grain) {
        super(iri, instance, tick, report, grain);
        this.scope = new TimeWindowing<>(instance.getScope(), size, b);
        this.windowBuffer = new WindowBuffer<>(instance.getScope());
    }

    public CSPARQLStreamToRelationOpSingle(IRI iri, Time time, Tick tick, Report report, ReportGrain grain, Scope<I, Collection<I>> scope) {
        super(iri, time, tick, report, grain);
        this.scope = scope;
        this.windowBuffer = new WindowBuffer<>(time.getScope());
    }

    @Override
    protected void windowing(I arg, long ts) {
        log.debug("Received element (" + arg + "," + ts + ")");

        if (time.getAppTime() > ts) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + arg + "," + ts + ")");
        }

        windowBuffer.add(arg, ts);

        Iterator<? extends Window> itWindow = scope.apply(arg, ts, windowBuffer);

        itWindow.forEachRemaining((Consumer<Window>) window -> {
            /*
            The advancement of the buffer must be done here
            since the buffer needs to be updated before the report function is invoked
            due to the content-change reporting policy.
             */
            windowBuffer.advance(window);

            if(report.report(window, windowBuffer, ts, System.currentTimeMillis())){
                ticker.tick(ts,window);
            }
        });
    }

    @Override
    public Time time() {
        return time;
    }

    @Override
    public Content<I, Collection<I>> content(long now) {
        return windowBuffer;
    }

    @Override
    public List<Content<I, Collection<I>>> getContents(long now) {
        return Collections.singletonList(windowBuffer);
    }

    @Override
    public TimeVarying<Collection<I>> get() {
        return null;
    }

    @Override
    public Content<I, Collection<I>> compute(long t_e, Window w) {
        time.setAppTime(t_e);
        return setVisible(t_e, w, windowBuffer);
    }

    @Override
    public void link(ContinuousQueryExecution context) {
        this.addObserver((Observer) context);
    }
}
