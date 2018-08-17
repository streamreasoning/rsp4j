package it.polimi.yasper.core.spe.windowing.assigner;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.ContentGraph;
import it.polimi.yasper.core.spe.content.EmptyContent;
import it.polimi.yasper.core.spe.exceptions.OutOfOrderElementException;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.windowing.definition.Window;
import it.polimi.yasper.core.spe.windowing.definition.WindowImpl;
import it.polimi.yasper.core.stream.StreamElement;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.*;
import java.util.stream.Collectors;

//TODO rename as C-SPARQL window operator
@Log4j
public class CQELSWindowAssigner extends ObservableWindowAssigner implements Observer {

    private final long a;

    private Map<Window, Content> windows;
    private List<StreamElement> r_stream;
    private List<StreamElement> d_stream;

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
        this.r_stream = new ArrayList<>();
        this.d_stream = new ArrayList<>();
    }

    @Override
    public Content getContent(long t_e) {
        Optional<Window> max = windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent())
            return windows.get(max.get());

        return new EmptyContent();
    }

    @Override
    public List<Content> getContents(long t_e) {
        return windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(windows::get).collect(Collectors.toList());
    }


    @Override
    public void update(Observable o, Object arg) {
        windowing((StreamElement) arg);
    }

    protected void windowing(StreamElement e) {
        log.debug("Received element (" + e.getContent() + "," + e.getTimestamp() + ")");
        long t_e = e.getTimestamp();

        if (time.getAppTime() > t_e) {
            log.error("OUT OF ORDER NOT HANDLED");
            throw new OutOfOrderElementException("(" + e.getContent() + "," + e.getTimestamp() + ")");
        }

        Window active = scope(t_e);
        Content content = windows.get(active);

        r_stream.stream().filter(ee -> ee.getTimestamp() < active.getO()).forEach(d_stream::add);

        r_stream.stream().filter(ee -> ee.getTimestamp() >= active.getO()).forEach(content::add);

        r_stream.add(e);
        content.add(e);

        if (report.report(active, content, t_e, System.currentTimeMillis())) {
            tick(t_e, active);
        }


        //REMOVE ALL THE WINDOWS THAT CONTAIN DSTREAM ELEMENTS
        //Theoretically active window has always size 1
        d_stream.forEach(ee -> {
            log.debug("Evicting [" + ee + "]");

            windows.forEach((window, content1) -> {
                if (window.getO() <= ee.getTimestamp() && window.getC() < ee.getTimestamp())
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
        Content content = windows.containsKey(w) ? windows.get(w) : new EmptyContent();
        time.setAppTime(t_e);
        return content;
    }

}
