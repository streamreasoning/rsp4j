package it.polimi.yasper.core.timevarying;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.engine.RSPListener;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.enums.Report;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 05/07/2017.
 */
@Log4j
@Getter
public abstract class TimeVaryingGraph extends Observable implements RSPListener, Observer {

    protected WindowOperator window_operator;
    protected InstantaneousItem graph;
    protected Maintenance maintenance;

    public TimeVaryingGraph(Maintenance maintenance, InstantaneousItem g, WindowOperator window_operator) {
        this.window_operator = window_operator;
        this.maintenance = maintenance;
        this.graph = g;

    }

    @Override
    public synchronized void update(EventBean[] newData, EventBean[] oldData, EPStatement stmt, EPServiceProvider esp) {
        long currentTime = esp.getEPRuntime().getCurrentTime();
        long l = System.currentTimeMillis();

        log.debug("[" + Thread.currentThread() + "][" + l + "] FROM STATEMENT: " + stmt.getText() + " AT "
                + currentTime);

        //TODO
        if (!window_operator.equals(stmt)) {
            log.debug("Window Operator does not coincide with Statement");
        }

        graph.setTimestamp(currentTime);

        window_operator.DStreamUpdate(graph, oldData, maintenance);
        window_operator.IStreamUpdate(graph, newData);
        setChanged();
    }


    public abstract WindowOperator getTriggeringStatement();

    public long getTimestamp() {
        return graph.getTimestamp();
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        update((long) arg);
        setChanged();
        notifyObservers(Report.PARTIAL);
    }

    public synchronized void update(long t) {
        EventBean[] windowContent = window_operator.getWindowContent(t);
        window_operator.DStreamUpdate(graph, null, Maintenance.NAIVE);
        window_operator.IStreamUpdate(graph, windowContent);
        graph.setTimestamp(t);
    }

}


