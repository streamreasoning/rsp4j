package it.polimi.sr.rsp.onsper.spe.operators.s2r.assigner;

import it.polimi.sr.rsp.onsper.rspql.TimeVaryingRelation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.exceptions.OutOfOrderElementException;
import it.polimi.yasper.core.operators.s2r.execution.assigner.ObservableWindowAssigner;
import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.operators.s2r.execution.instance.WindowImpl;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import lombok.extern.log4j.Log4j;
import it.polimi.sr.rsp.onsper.spe.content.ContentRel;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.jooq.lambda.tuple.Tuple;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
public class VCSPARQLWindowAssigner extends ObservableWindowAssigner<Tuple, Relation<Tuple>> {

    private final long a, b;
    private final long t0;
    private final Graph mapping;
    private final Relation<Tuple> relation;
    private final StreamSchema schema;

    private Map<Window, Content<Tuple, Relation<Tuple>>> active_windows;
    private Set<Window> to_evict;
    private long tc0;
    private long toi;

    public VCSPARQLWindowAssigner(IRI iri, long a, long b, long t0, long tc0, Time instance, StreamSchema schema, Graph mapping, Relation<Tuple> relation, Tick tick, Report report, ReportGrain grain) {
        super(iri, instance, tick, report, grain);
        this.a = a;
        this.b = b;
        this.t0 = t0;
        this.tc0 = tc0;
        this.toi = 0;
        this.active_windows = new HashMap<>();
        this.to_evict = new HashSet<>();
        this.mapping = mapping;
        this.schema = schema;
        this.relation = relation;
    }


    @Override
    public Time time() {
        return time;
    }

    @Override
    public Content<Tuple, Relation<Tuple>> getContent(long t_e) {
        Optional<Window> max = active_windows.keySet().stream()
                .filter(w -> w.getO() < t_e && w.getC() <= t_e)
                .max(Comparator.comparingLong(Window::getC));

        if (max.isPresent()) {
            return active_windows.get(max.get());
        }

        return new ContentRel();
    }


    @Override
    public List<Content<Tuple, Relation<Tuple>>> getContents(long t_e) {
        return active_windows.keySet().stream()
                .filter(w -> w.getO() <= t_e && t_e < w.getC())
                .map(active_windows::get).collect(Collectors.toList());
    }

    @Override
    public Content<Tuple, Relation<Tuple>> compute(long t_e, Window w) {
        Content<Tuple, Relation<Tuple>> content = active_windows.containsKey(w) ? active_windows.get(w) : new ContentRel();
        time.setAppTime(t_e);
        return setVisible(t_e, w, content);
    }

    public TimeVarying set(SDS content) {
        this.addObserver((Observer) content);
        return new TimeVaryingRelation(this.iri, schema, relation, this);
    }

    protected void windowing(Tuple e, long timestamp) {
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
                        Content<Tuple, Relation<Tuple>> tupleRelationContent = active_windows.get(w);
                        tupleRelationContent.add(e);
                    } else if (t_e > w.getC()) {
                        log.debug("Scheduling for Eviction [" + w.getO() + "," + w.getC() + ")");
                        schedule_for_eviction(w);
                    }
                });


        switch (grain) {
            case MULTIPLE:
                active_windows.keySet().stream()
                        .filter(w -> report.report(w, null, t_e, System.currentTimeMillis()))
                        .forEach(w -> this.ticker.tick(t_e, w));
                break;
            case SINGLE:
            default:
                active_windows.keySet().stream()
                        .filter(w -> report.report(w, null, t_e, System.currentTimeMillis()))
                        .max(Comparator.comparingLong(Window::getC))
                        .ifPresent(window -> this.ticker.tick(t_e, window));
        }

        //TODO shouldn't we evaluate setVisible.setVisible when we materialize the e?
        //TODO Tick regulates whether we compute, setVisible only if we see the results.

        //TODO eviction, should we notify evicted windows to interested observers?

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
                    .computeIfAbsent(new WindowImpl(o_i, o_i + a), x -> new ContentRel());
            o_i += b;

        } while (o_i <= t_e);

    }


    private void schedule_for_eviction(Window w) {
        to_evict.add(w);
    }


}
