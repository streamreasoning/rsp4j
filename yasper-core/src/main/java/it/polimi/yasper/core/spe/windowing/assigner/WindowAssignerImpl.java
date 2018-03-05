package it.polimi.yasper.core.spe.windowing.assigner;

import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.simple.windowing.TimeVaryingGraph;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.ContentGraph;
import it.polimi.yasper.core.spe.content.EmptyContent;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.exceptions.OutOfOrderElementException;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.stream.StreamElement;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.time.TimeInstant;
import it.polimi.yasper.core.spe.windowing.definition.Window;
import it.polimi.yasper.core.spe.windowing.definition.WindowImpl;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
public class WindowAssignerImpl extends Observable implements WindowAssigner, Observer {

    private final Stream stream;
    private final long a, b;
    private final Time time;
    private final long t0;
    private final IRI iri;

    private Map<Window, Content> active_windows;
    private Set<Window> to_evict;
    private Tick tick;
    private ReportGrain aw;
    private Report report;
    private long tc0;
    private long toi;

    public WindowAssignerImpl(IRI iri, Stream s, long a, long b, long t0, long tc0) {
        this.iri = iri;
        this.stream = s;
        this.a = a;
        this.b = b;
        this.t0 = t0;
        this.tc0 = tc0;
        this.toi = 0;
        this.active_windows = new HashMap<>();
        this.to_evict = new HashSet<>();
        this.time = TimeFactory.getInstance();
        this.stream.addWindowAssiger(this);
    }


    @Override
    public Report getReport() {
        return report;
    }

    @Override
    public Tick getTick() {
        return tick;
    }

    @Override
    public Content getContent(long t_e) {
        Optional<Window> max = active_windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return active_windows.get(max.get());

        return new EmptyContent();
    }

    @Override
    public List<Content> getContents(long t_e) {
        return active_windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(active_windows::get).collect(Collectors.toList());
    }

    @Override
    public void setReport(Report report) {
        this.report = report;
    }

    public void setTick(Tick t) {
        this.tick = t;
    }

    @Override
    public TimeVaryingGraph setView(View view) {
        view.addObserver(this);
        return new TimeVaryingGraph(iri, this);
    }

    @Override
    public void setReportGrain(ReportGrain aw) {
        this.aw = aw;
    }

    @Override
    public void notify(StreamElement arg) {
        windowing(arg);
    }


    @Override
    public void update(Observable o, Object arg) {
        windowing((StreamElement) arg);
    }

    private void windowing(StreamElement e) {
        log.debug("Received element (" + e.getContent() + "," + e.getTimestamp() + ")");
        long t_e = e.getTimestamp();

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e.getContent() + "," + e.getTimestamp() + ")");
        }

        scope(t_e);

        active_windows.keySet().forEach(
                w -> {
                    log.debug("Processing Window [" + w.getO() + "," + w.getC() + ") for element (" + e.getContent() + "," + e.getTimestamp() + ")");
                    if (w.getO() <= t_e && t_e < w.getC()) {
                        log.debug("Adding element [" + e + "] to Window [" + w.getO() + "," + w.getC() + ")");
                        active_windows.get(w).add(e);
                    } else if (t_e > w.getC()) {
                        log.debug("Scheduling for Eviction [" + w.getO() + "," + w.getC() + ")");
                        schedule_for_eviction(w);
                    }
                });


        switch (aw) {
            case MULTIPLE:
                active_windows.keySet().stream()
                        .filter(w -> report.report(w, null, t_e, System.currentTimeMillis()))
                        .forEach(w -> tick(t_e, w));
                break;
            case SINGLE:
            default:
                active_windows.keySet().stream()
                        .filter(w -> report.report(w, null, t_e, System.currentTimeMillis()))
                        .max(Comparator.comparingLong(Window::getC))
                        .ifPresent(window -> tick(t_e, window));
        }

        //TODO shouldn't we evaluate setVisible.setVisible when we materialize the content?
        //TODO Tick regulates whether we compute, setVisible only if we see the results.

        //TODO eviction

        to_evict.forEach(w -> {
            log.debug("Evicting [" + w.getO() + "," + w.getC() + ")");
            active_windows.remove(w);
            if (toi < w.getC())
                toi = w.getC() + b;
        });
        to_evict.clear();
    }

    private void scope(long t_e) {
        long c_sup = (long) Math.ceil(((double) Math.abs(t_e - tc0) / (double) b)) * b;
        long o_i = c_sup - a;
        log.debug("Calculating the Windows to Open. First one opens at [" + o_i + "] and closes at [" + c_sup + "]");

        do {
            log.debug("Computing Window [" + o_i + "," + (o_i + a) + ") if absent");

            active_windows
                    .computeIfAbsent(new WindowImpl(o_i, o_i + a), x -> new ContentGraph());
            o_i += b;

        } while (o_i <= t_e);

    }


    private void schedule_for_eviction(Window w) {
        to_evict.add(w);
    }

    /**
     * The Tick dimension in our model defines the condition which drives an SPE
     * to take action on its input (also referred to as “window state change” or “window re-evaluation” [13]).
     * Like Report, Tick is also part of a system’s inter- nal execution model.
     * While some systems react to individual tuples as they arrive, others collectively
     * react to all or subsets of tuples with the same tapp value.
     * During our analysis, we have identified three main ways that di↵erent systems “tick”:
     * (a) tuple-driven, where each tuple arrival causes a system to react;
     * (b) time-driven, where the progress of tapp causes a system to react;
     * (c) batch-driven, where either a new batch arrival or the progress of tapp causes a system to react.
     **/

    private Content tick(long t_e, Window w) {
        TimeFactory.getEvaluationTimeInstants().add(new TimeInstant(t_e));
        Content c = null;
        switch (tick) {
            case TIME_DRIVEN:
                if (t_e > time.getAppTime()) {
                    c = compute(t_e, w);
                }
                break;
            case TUPLE_DRIVEN:
                c = compute(t_e, w);
                break;
            case BATCH_DRIVEN:
            default:
                c = compute(t_e, w);
                break;
        }

        return setVisible(t_e, w, c);

    }

    private Content compute(long t_e, Window w) {
        Content content = active_windows.containsKey(w) ? active_windows.get(w) : new EmptyContent();
        time.setAppTime(t_e);
        return content;
    }

    private Content setVisible(long t_e, Window w, Content c) {
        //TODO the reporting makes the content visible
        // but the execution of the query is not
        log.debug("Report [" + w.getO() + "," + w.getC() + ") with Content " + c + "");
        setChanged();
        notifyObservers(t_e);
        return c;
    }
}
