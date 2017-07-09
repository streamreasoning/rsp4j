package it.polimi.yasper.core.jena;

import it.polimi.yasper.core.query.operators.s2r.WindowModel;
import org.apache.jena.mem.GraphMem;

/**
 * Created by riccardo on 05/07/2017.
 */
public class TimeVaryingGraphBase extends GraphMem implements JenaTimeVaryingGraph {

    private long last_timestamp;
    private WindowModel window;


    public TimeVaryingGraphBase() {
        this.last_timestamp = -1;
        this.window = null;
    }

    public TimeVaryingGraphBase(long last_timestamp, WindowModel window) {
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
    public WindowModel getWindowOperator() {
        return window;
    }

    @Override
    public void setWindowOperator(WindowModel w) {
        this.window = w;
    }
}
