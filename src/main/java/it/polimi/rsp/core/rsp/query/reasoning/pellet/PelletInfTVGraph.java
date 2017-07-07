package it.polimi.rsp.core.rsp.query.reasoning.pellet;

import it.polimi.rsp.core.rsp.query.reasoning.TimeVaryingInfGraph;
import it.polimi.rsp.core.rsp.sds.windows.WindowModel;
import openllet.jena.PelletInfGraph;
import openllet.jena.PelletReasoner;
import openllet.jena.graph.loader.GraphLoader;
import org.apache.jena.graph.Graph;

/**
 * Created by riccardo on 05/07/2017.
 */
public class PelletInfTVGraph extends PelletInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private WindowModel window;

    public PelletInfTVGraph(Graph graph, PelletReasoner pellet, GraphLoader loader, WindowModel w, long last_timestamp) {
        super(graph, pellet, loader);
        this.last_timestamp = last_timestamp;
        this.window = w;
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
