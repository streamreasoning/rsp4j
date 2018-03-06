package it.polimi.jasper.engine.reasoning.rulesys;

import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.RETERuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class RETERuleInstantaneousInfTVGraph extends RETERuleInfGraph implements InfGraph {

    private Graph igraph;

    public RETERuleInstantaneousInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, Graph i) {
        super(reasoner, rules, schema);
        this.igraph = i;
    }


}
