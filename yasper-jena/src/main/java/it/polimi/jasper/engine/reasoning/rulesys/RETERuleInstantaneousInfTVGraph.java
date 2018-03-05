package it.polimi.jasper.engine.reasoning.rulesys;

import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.RETERuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class RETERuleInstantaneousInfTVGraph extends RETERuleInfGraph implements InstantaneousInfGraph {

    private long last_timestamp;
    private JenaGraph igraph;

    public RETERuleInstantaneousInfTVGraph() {
        super(null, null);
    }

    public RETERuleInstantaneousInfTVGraph(Reasoner reasoner, Graph schema, long last_timestamp, JenaGraph i) {
        super(reasoner, schema);
        this.last_timestamp = last_timestamp;
        this.igraph = i;
    }


    public RETERuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, long last_timestamp, JenaGraph i) {
        super(reasoner, rules, schema);
        this.last_timestamp = last_timestamp;
        this.igraph = i;
    }

    public RETERuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, Graph data,
                                           long last_timestamp, JenaGraph i) {
        super(reasoner, rules, schema, data);
        this.last_timestamp = last_timestamp;
        this.igraph = i;
    }

    @Override
    public long getTimestamp() {
        return last_timestamp;
    }

    @Override
    public void setTimestamp(long ts) {
        this.last_timestamp = ts;
    }


}
