package it.polimi.jasper.engine.reasoning.rulesys;

import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.jasper.engine.reasoning.InstantaneousInfGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.FBRuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class FBRuleInstantaneousInfTVGraph extends FBRuleInfGraph implements InstantaneousInfGraph {

    private long last_timestamp;
    private JenaGraph igraph;

    public FBRuleInstantaneousInfTVGraph(Reasoner reasoner, Graph schema, long last_timestamp, JenaGraph w) {
        super(reasoner, schema);
        this.last_timestamp = last_timestamp;
        this.igraph = w;
    }


    public FBRuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, long last_timestamp, JenaGraph w) {
        super(reasoner, rules, schema);
        this.last_timestamp = last_timestamp;
        this.igraph = w;
    }

    public FBRuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, Graph data,
                                         long last_timestamp, JenaGraph w) {
        super(reasoner, rules, schema, data);
        this.last_timestamp = last_timestamp;
        this.igraph = w;
    }

    public long getTimestamp() {
        return last_timestamp;
    }

    public void setTimestamp(long ts) {
        this.last_timestamp = ts;
    }



}
