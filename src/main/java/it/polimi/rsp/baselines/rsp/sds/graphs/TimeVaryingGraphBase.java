package it.polimi.rsp.baselines.rsp.sds.graphs;

import it.polimi.rsp.baselines.rsp.sds.windows.WindowModel;
import org.apache.jena.mem.GraphMem;

/**
 * Created by riccardo on 05/07/2017.
 */
public class TimeVaryingGraphBase extends GraphMem implements TimeVaryingGraph {

    private long last_timestamp;
    private WindowModel window;


    public TimeVaryingGraphBase(long last_timestamp, WindowModel window) {
        this.last_timestamp = last_timestamp;
        this.window = window;
    }

    @Override
    public long getTimestamp() {
        return last_timestamp;
    }

    @Override
    public WindowModel getWindowOperator() {
        return window;
    }

    @Override
    public void setWindowOperator(WindowModel w) {
        this.window = w;
    }

    @Override
    public void setTimestamp(long ts) {
        this.last_timestamp = ts;
    }
}
