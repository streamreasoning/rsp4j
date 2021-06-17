package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.exceptions.OutOfOrderElementException;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.ObservableStreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.WindowImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.yasper.sds.TimeVaryingObject;

import java.util.*;
import java.util.stream.Collectors;

//TODO rename as C-SPARQL window operator
@Log4j
public class CQELSTimeWindowOperatorBinding<T1, T2> extends ObservableStreamToRelationOp<T1, T2> {

    private final long a;

    private Map<Window, Content<T1, T2>> windows;
    private Map<T1, Long> r_stream;
    private Map<T1, Long> d_stream;

    private Set<Window> to_evict;
    private long tc0;
    private long toi;


    public CQELSTimeWindowOperatorBinding(IRI iri, long a, Time instance, Tick tick, Report report, ReportGrain grain, ContentFactory<T1, T2> cf) {
        super(iri, instance, tick, report, grain, cf);
        this.a = a;
        this.tc0 = instance.getScope();
        this.toi = 0;
        this.windows = new HashMap<>();
        this.to_evict = new HashSet<>();
        this.r_stream = new HashMap<>();
        this.d_stream = new HashMap<>();
    }

    @Override
    public Time time() {
        return time;
    }

    @Override
    public Content<T1, T2> content(long t_e) {
        Optional<Window> max = windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return windows.get(max.get());

        return cf.createEmpty();
    }

    @Override
    public List<Content<T1, T2>> getContents(long t_e) {
        return windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(windows::get).collect(Collectors.toList());
    }


    public void windowing(T1 e, long ts) {
        log.debug("Received element (" + e + "," + ts + ")");
        long t_e = ts;

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e + "," + ts + ")");
        }

        Window active = scope(t_e);
        Content<T1, T2> content = windows.get(active);

        r_stream.entrySet().stream().filter(ee -> ee.getValue() < active.getO()).forEach(ee -> d_stream.put(ee.getKey(), ee.getValue()));

        r_stream.entrySet().stream().filter(ee -> ee.getValue() >= active.getO()).map(Map.Entry::getKey).forEach(content::add);

        r_stream.put(e, ts);
        content.add(e);

        if (report.report(active, content, t_e, System.currentTimeMillis())) {
            ticker.tick(t_e, active);
        }


        //REMOVE ALL THE WINDOWS THAT CONTAIN DSTREAM ELEMENTS
        //Theoretically active window has always size 1
        d_stream.entrySet().forEach(ee -> {
            log.debug("Evicting [" + ee + "]");

            windows.forEach((window, content1) -> {
                if (window.getO() <= ee.getValue() && window.getC() < ee.getValue())
                    schedule_for_eviction(window);

            });

            r_stream.remove(ee);
        });

        to_evict.forEach(windows::remove);
        to_evict.clear();
    }

    private Window scope(long t_e) {
        long o_i = t_e - a;
        log.debug("Calculating the Windows to Open. First one opens at [" + o_i + "] and closes at [" + t_e + "]");
        log.debug("Computing Window [" + o_i + "," + (o_i + a) + ") if absent");

        WindowImpl active = new WindowImpl(o_i, t_e);
        windows.computeIfAbsent(active, window -> cf.create());
        return active;
    }

    private void schedule_for_eviction(Window w) {
        to_evict.add(w);
    }

    public Content<T1, T2> compute(long t_e, Window w) {
        Content<T1, T2> content = windows.containsKey(w) ? windows.get(w) : cf.createEmpty();
        time.setAppTime(t_e);
        return setVisible(t_e, w, content);
    }

    @Override
    public StreamToRelationOp<T1, T2> link(ContinuousQueryExecution context) {
        this.addObserver((Observer) context);
        return this;
    }


    @Override
    public TimeVarying<T2> get() {
        return new TimeVaryingObject<>(this, iri);
    }


    public TimeVarying<T2> apply(WebDataStream<T1> s) {
        s.addConsumer(this);
        return new TimeVaryingObject(this, RDFUtils.createIRI(s.uri()));
    }

}

