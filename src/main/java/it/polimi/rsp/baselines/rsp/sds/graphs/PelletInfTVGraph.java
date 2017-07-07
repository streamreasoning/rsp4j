package it.polimi.rsp.baselines.rsp.sds.graphs;

import it.polimi.rsp.baselines.rsp.sds.windows.WindowModel;
import it.polimi.rsp.baselines.rsp.sds.windows.WindowOperator;
import openllet.jena.PelletInfGraph;
import openllet.jena.PelletReasoner;
import openllet.jena.graph.loader.GraphLoader;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.FBRuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

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
