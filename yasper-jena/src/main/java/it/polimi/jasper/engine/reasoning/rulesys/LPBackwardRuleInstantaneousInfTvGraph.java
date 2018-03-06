package it.polimi.jasper.engine.reasoning.rulesys;

import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import org.apache.jena.reasoner.rulesys.impl.LPRuleStore;

/**
 * Created by riccardo on 05/07/2017.
 */
public class LPBackwardRuleInstantaneousInfTvGraph extends LPBackwardRuleInfGraph implements InfGraph {

    private long last_timestamp;
    private Graph igraph;

    public LPBackwardRuleInstantaneousInfTvGraph(Reasoner reasoner, LPRuleStore ruleStore, Graph data, Graph schema, Graph igraph) {
        super(reasoner, ruleStore, data, schema);
        this.last_timestamp = last_timestamp;
        this.igraph = igraph;
    }


    public long getTimestamp() {
        return last_timestamp;
    }

    public void setTimestamp(long ts) {
        this.last_timestamp = ts;
    }


}