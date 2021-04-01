package org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner;

import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.tick.Ticker;
import org.streamreasoning.rsp4j.api.secret.tick.secret.TickerFactory;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;

import java.util.Observable;

@Log4j
public abstract class ObservableStreamToRelationOp<E, O> extends Observable implements StreamToRelationOp<E, O> {

    protected Tick tick;
    protected ReportGrain grain;
    protected Report report;
    protected final Ticker ticker;
    protected final Time time;
    protected final IRI iri;

    protected ObservableStreamToRelationOp(IRI iri, Time time, Tick tick, Report report, ReportGrain grain) {
        this.time = time;
        this.iri = iri;
        this.tick = tick;
        this.ticker = TickerFactory.tick(tick, this);
        this.report = report;
        this.grain = grain;
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

    protected Content<E,O> setVisible(long t_e, Window w, Content<E,O> c) {
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
