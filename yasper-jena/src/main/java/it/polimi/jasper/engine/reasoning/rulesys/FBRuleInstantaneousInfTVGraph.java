package it.polimi.jasper.engine.reasoning.rulesys;

import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.FBRuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class FBRuleInstantaneousInfTVGraph extends FBRuleInfGraph implements InfGraph {

    private Graph igraph;


    public FBRuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, Graph w) {
        super(reasoner, rules, schema);
        this.igraph = w;
    }


    @Override
    public String toString() {
        return "FBRuleInstantaneousInfTVGraph@"+hashCode();
    }
}
