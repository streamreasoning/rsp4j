package it.polimi.jasper.engine.reasoning.pellet;

import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import openllet.jena.PelletInfGraph;
import openllet.jena.PelletReasoner;
import openllet.jena.graph.loader.GraphLoader;
import org.apache.jena.graph.Graph;

/**
 * Created by riccardo on 05/07/2017.
 */
public class PelletInfTVGraph extends PelletInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private WindowOperator window;

    public PelletInfTVGraph(Graph graph, PelletReasoner pellet, GraphLoader loader, WindowOperator w, long last_timestamp) {
        super(graph, pellet, loader);
        this.last_timestamp = last_timestamp;
        this.window = w;
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
        this.window = w;
    }
}
