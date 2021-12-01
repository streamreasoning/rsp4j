package org.streamreasoning.rsp4j.esper.sds.tv;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import org.streamreasoning.rsp4j.esper.secret.content.ContentEventBean;

import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.enums.Maintenance;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.WindowImpl;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.Report;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 05/07/2017.
 */
@Log4j
@Getter
public class EsperTimeVaryingGeneric<I, O> extends Observable implements StatementAwareUpdateListener, TimeVarying<O> {

    private final SDS sds;
    protected Report report;
    protected StreamToRelationOp<I, O> wa;
    protected Maintenance maintenance;
    protected long now;

    protected ContentEventBean<I, O> c;

    public EsperTimeVaryingGeneric(ContentEventBean<I, O> c, Maintenance maintenance, Report report, StreamToRelationOp<I, O> wa, SDS<O> sds) {
        this.maintenance = maintenance;
        this.wa = wa;
        this.report = report;
        this.sds = sds;
        this.addObserver((Observer) sds);
        this.c = c;

    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider eps) {
        long event_time = eps.getEPRuntime().getCurrentTime();

        long systime = System.currentTimeMillis();

        this.c.update(newData, oldData, event_time);

        //TODO content is not from yasper
        //TODO update window (was null)
        if (report.report(new WindowImpl(0,0), (Content) c, event_time, systime)) {
            log.debug("[" + Thread.currentThread() + "][" + systime + "] FROM STATEMENT: " + stmt.getText() + " AT "
                    + event_time);

            setChanged();
            notifyObservers(event_time);
        }
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void materialize(long ts) {
        if (this.c.getTimeStampLastUpdate() < ts) {
            this.c.replace(this.wa.content(ts).coalesce());
        } else
            this.c.coalesce();
    }

    @Override
    public O get() {
        return c.coalesce();
    }

    @Override
    public String iri() {
        return "";
    }

    @Override
    public boolean named() {
        return false;
    }


}
