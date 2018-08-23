package it.polimi.yasper.core.spe.windowing.assigner;

import it.polimi.yasper.core.spe.Tick;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.time.TimeInstant;
import it.polimi.yasper.core.spe.windowing.definition.Window;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.Observable;

@Log4j
public abstract class ObservableWindowAssigner<E> extends Observable implements WindowAssigner<E> {

    protected Tick tick;
    protected ReportGrain aw;
    protected Report report;
    protected final Time time;
    protected final IRI iri;

    protected ObservableWindowAssigner(IRI iri, Time time) {
        this.time = time;
        this.iri = iri;
    }

    @Override
    public void report(Report report) {
        this.report = report;
    }

    public void tick(Tick t) {
        this.tick = t;
    }

    @Override
    public void report_grain(ReportGrain aw) {
        this.aw = aw;
    }

    @Override
    public void notify(E arg, long ts) {
        windowing(arg, ts);
    }

    @Override
    public Report report() {
        return report;
    }

    @Override
    public Tick tick() {
        return tick;
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

    protected Content tick(long t_e, Window w) {
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

    private Content setVisible(long t_e, Window w, Content c) {
        log.debug("Report [" + w.getO() + "," + w.getC() + ") with Content " + c + "");
        setChanged();
        notifyObservers(t_e);
        return c;
    }

    protected abstract void windowing(E arg, long ts);

    protected abstract Content compute(long t_e, Window w);

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }
}
