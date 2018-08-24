package it.polimi.yasper.core.spe.operators.s2r.execution.assigner;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.spe.tick.Ticker;
import it.polimi.yasper.core.spe.tick.TickerImpl;
import it.polimi.yasper.core.spe.time.Time;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.Observable;

@Log4j
public abstract class ObservableWindowAssigner<E> extends Observable implements WindowAssigner<E> {

    protected Tick tick;
    protected ReportGrain aw;
    protected Report report;
    protected final Ticker ticker;
    protected final Time time;
    protected final IRI iri;

    protected ObservableWindowAssigner(IRI iri, Time time, Ticker ticker) {
        this.time = time;
        this.iri = iri;
        this.ticker = ticker;
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

    protected Content<E> setVisible(long t_e, Window w, Content<E> c) {
        log.debug("Report [" + w.getO() + "," + w.getC() + ") with Content " + c + "");
        setChanged();
        notifyObservers(t_e);
        return c;
    }

    protected abstract void windowing(E arg, long ts);

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }
}
