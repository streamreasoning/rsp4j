package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.exceptions.OutOfOrderElementException;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.ObservableStreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.WindowImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.sds.TimeVaryingObject;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
public class CSPARQLStreamToRelationOp<I, W> extends ObservableStreamToRelationOp<I, W> {

    private final long a, b;

    private Map<Window, Content<I, W>> active_windows;
    private Set<Window> to_evict;
    private long t0;
    private long toi;

    public CSPARQLStreamToRelationOp(IRI iri, long a, long b, Time instance, Tick tick, Report report, ReportGrain grain, ContentFactory<I, W> cf) {
        super(iri, instance, tick, report, grain, cf);
        this.a = a;
        this.b = b;
        this.t0 = instance.getScope();
        this.toi = 0;
        this.active_windows = new HashMap<>();
        this.to_evict = new HashSet<>();
    }

    @Override
    public Time time() {
        return time;
    }

    @Override
    public Content<I, W> content(long t_e) {
        Optional<Window> max = active_windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return active_windows.get(max.get());

        return cf.createEmpty();
    }

    @Override
    public List<Content<I, W>> getContents(long t_e) {
        return active_windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(active_windows::get).collect(Collectors.toList());
    }

    public void windowing(I e, long timestamp) {

        log.debug("Received element (" + e + "," + timestamp + ")");
        long t_e = timestamp;

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e + "," + timestamp + ")");
        }

        scope(t_e);

        active_windows.keySet().forEach(
                w -> {
                    log.debug("Processing Window [" + w.getO() + "," + w.getC() + ") for element (" + e + "," + timestamp + ")");
                    if (w.getO() <= t_e && t_e < w.getC()) {
                        log.debug("Adding element [" + e + "] to Window [" + w.getO() + "," + w.getC() + ")");
                        active_windows.get(w).add(e);
                    } else if (t_e > w.getC()) {
                        log.debug("Scheduling for Eviction [" + w.getO() + "," + w.getC() + ")");
                        schedule_for_eviction(w);
                    }
                });


        active_windows.keySet().stream()
                .filter(w -> report.report(w, null, t_e, System.currentTimeMillis()))
                .max(Comparator.comparingLong(Window::getC))
                .ifPresent(window -> ticker.tick(t_e, window));

        to_evict.forEach(w -> {
            log.debug("Evicting [" + w.getO() + "," + w.getC() + ")");
            active_windows.remove(w);
            if (toi < w.getC())
                toi = w.getC() + b;
        });
        to_evict.clear();
    }

    private void scope(long t_e) {
        long c_sup = (long) Math.ceil(((double) Math.abs(t_e - t0) / (double) b)) * b;
        long o_i = c_sup - a;
        log.debug("Calculating the Windows to Open. First one opens at [" + o_i + "] and closes at [" + c_sup + "]");

        do {
            log.debug("Computing Window [" + o_i + "," + (o_i + a) + ") if absent");

            active_windows
                    .computeIfAbsent(new WindowImpl(o_i, o_i + a), x -> cf.create());
            o_i += b;

        } while (o_i <= t_e);

    }


    private void schedule_for_eviction(Window w) {
        to_evict.add(w);
    }


    public Content<I, W> compute(long t_e, Window w) {
        Content<I, W> content = active_windows.containsKey(w) ? active_windows.get(w) : cf.createEmpty();
        time.setAppTime(t_e);
        return setVisible(t_e, w, content);
    }

    @Override
    public CSPARQLStreamToRelationOp<I, W> link(ContinuousQueryExecution<I, W,?, ?> context) {
        this.addObserver((Observer) context);
        return this;
    }

    @Override
    public TimeVarying<W> apply(DataStream<I> s) {
        s.addConsumer(this);
        return new TimeVaryingObject(this, iri);
    }


    @Override
    public TimeVarying<W> get() {
        return new TimeVaryingObject<>(this, iri);
    }


}
