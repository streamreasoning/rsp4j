package simple.windowing;

import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.tvg.TimeVaryingGraph;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.ContentGraph;
import it.polimi.yasper.core.spe.content.EmptyGraphContent;
import it.polimi.yasper.core.spe.exceptions.OutOfOrderElementException;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.windowing.assigner.ObservableWindowAssigner;
import it.polimi.yasper.core.spe.windowing.definition.Window;
import it.polimi.yasper.core.spe.windowing.definition.WindowImpl;
import it.polimi.yasper.core.rspql.RDFUtils;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

import java.util.*;
import java.util.stream.Collectors;

//TODO rename as C-SPARQL window operator
@Log4j
public class CQELSWindowAssigner extends ObservableWindowAssigner<Graph> implements Observer {

    private final long a;

    private Map<Window, Content<Graph>> windows;
    private Map<Graph, Long> r_stream;
    private Map<Graph, Long> d_stream;

    private Set<Window> to_evict;
    private long tc0;
    private long toi;


    public CQELSWindowAssigner(IRI iri, long a, long tc0, Time instance) {
        super(iri, instance);
        this.a = a;
        this.tc0 = tc0;
        this.toi = 0;
        this.windows = new HashMap<>();
        this.to_evict = new HashSet<>();
        this.r_stream = new HashMap<>();
        this.d_stream = new HashMap<>();
    }

    @Override
    public Content<Graph> getContent(long t_e) {
        Optional<Window> max = windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return windows.get(max.get());

        return new EmptyGraphContent();
    }

    @Override
    public List<Content> getContents(long t_e) {
        return windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(windows::get).collect(Collectors.toList());
    }


    @Override
    public void update(Observable o, Object arg) {
    }

    protected void windowing(Graph e, long ts) {
        log.debug("Received element (" + e + "," + ts + ")");
        long t_e = ts;

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e + "," + ts + ")");
        }

        Window active = scope(t_e);
        Content<Graph> content = windows.get(active);

        r_stream.entrySet().stream().filter(ee -> ee.getValue() < active.getO()).forEach(ee -> d_stream.put(ee.getKey(), ee.getValue()));

        r_stream.entrySet().stream().filter(ee -> ee.getValue() >= active.getO()).map(Map.Entry::getKey).forEach(content::add);

        r_stream.put(e, ts);
        content.add(e);

        if (report.report(active, content, t_e, System.currentTimeMillis())) {
            tick(t_e, active);
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

    protected Content compute(long t_e, Window w) {
        Content content = windows.containsKey(w) ? windows.get(w) : new EmptyGraphContent();
        time.setAppTime(t_e);
        return content;
    }


    @Override
    public TimeVaryingGraph set(ContinuousQueryExecution content) {
        this.addObserver(content);
        //TODO Generalize the type of content using an ENUM
        return new TimeVaryingGraph(this, iri, RDFUtils.createGraph());
    }

}

