package simple.windowing;

import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.exceptions.OutOfOrderElementException;
import it.polimi.yasper.core.operators.s2r.execution.assigner.ObservableWindowAssigner;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.operators.s2r.execution.instance.WindowImpl;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.content.ContentGraph;
import it.polimi.yasper.core.secret.content.EmptyGraphContent;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import simple.querying.TimeVaryingGraph;

import java.util.*;
import java.util.stream.Collectors;

//TODO rename as C-SPARQL window operator
@Log4j
public class CQELSWindowAssigner extends ObservableWindowAssigner<Graph, Graph> {

    private final long a;

    private Map<Window, Content<Graph, Graph>> windows;
    private Map<Graph, Long> r_stream;
    private Map<Graph, Long> d_stream;

    private Set<Window> to_evict;
    private long tc0;
    private long toi;


    public CQELSWindowAssigner(IRI iri, long a, Time instance, Tick tick, Report report, ReportGrain grain) {
        super(iri, instance, tick, report, grain);
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
    public Content<Graph, Graph> getContent(long t_e) {
        Optional<Window> max = windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return windows.get(max.get());

        return new EmptyGraphContent();
    }

    @Override
    public List<Content<Graph, Graph>> getContents(long t_e) {
        return windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(windows::get).collect(Collectors.toList());
    }


    protected void windowing(Graph e, long ts) {
        log.debug("Received element (" + e + "," + ts + ")");
        long t_e = ts;

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e + "," + ts + ")");
        }

        Window active = scope(t_e);
        Content<Graph, Graph> content = windows.get(active);

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
        windows.computeIfAbsent(active, window -> new ContentGraph());
        return active;
    }

    private void schedule_for_eviction(Window w) {
        to_evict.add(w);
    }

    public Content<Graph, Graph> compute(long t_e, Window w) {
        Content<Graph, Graph> content = windows.containsKey(w) ? windows.get(w) : new EmptyGraphContent();
        time.setAppTime(t_e);
        return setVisible(t_e, w, content);
    }


    @Override
    public TimeVaryingGraph set(ContinuousQueryExecution content) {
        this.addObserver((Observer) content);
        //TODO Generalize the type of content using an ENUM
        return new TimeVaryingGraph(this, iri, RDFUtils.createGraph());
    }

}

