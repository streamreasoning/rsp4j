package it.polimi.jasper.sds;

import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import org.apache.jena.mem.GraphMem;

/**
 * Created by riccardo on 05/07/2017.
 */
public class TimeVaryingGraphBase extends GraphMem implements JenaTimeVaryingGraph {

    private long last_timestamp;
    private WindowOperator window;

    public TimeVaryingGraphBase() {
        this.last_timestamp = -1;
        this.window = null;
    }

    public TimeVaryingGraphBase(long last_timestamp, WindowOperator window) {
        this.last_timestamp = last_timestamp;
        this.window = window;
    }

    @Override
    public long getTimestamp() {
        return last_timestamp;
    }

    @Override
    public void setTimestamp(long ts) {
        this.last_timestamp = ts;
    }

    @Override
    public WindowOperator getWindowOperator() {
        return window;
    }

    @Override
    public void setWindowOperator(WindowOperator w) {

    }

}
