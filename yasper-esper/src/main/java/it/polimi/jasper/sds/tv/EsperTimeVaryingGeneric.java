package it.polimi.jasper.sds.tv;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;
import it.polimi.jasper.secret.content.ContentEventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.report.Report;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

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
    protected Assigner<I, O> wa;
    protected Maintenance maintenance;
    protected long now;

    protected ContentEventBean<I, O> c;

    public EsperTimeVaryingGeneric(ContentEventBean<I, O> c, Maintenance maintenance, Report report, Assigner<I, O> wa, SDS<O> sds) {
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
        if (report.report(null, (Content) c, event_time, systime)) {
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
            this.c.replace(this.wa.getContent(ts).coalesce());
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
