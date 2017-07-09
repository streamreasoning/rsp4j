package it.polimi.yasper.core.jena.query.reasoning.jena;

import it.polimi.yasper.core.jena.query.reasoning.TimeVaryingInfGraph;
import it.polimi.yasper.core.query.operators.s2r.WindowModel;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.FBRuleInfGraph;
import org.apache.jena.reasoner.rulesys.Rule;

import java.util.List;

/**
 * Created by riccardo on 05/07/2017.
 */
public class FBRuleInfTVGraph extends FBRuleInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private WindowModel window;


    public FBRuleInfTVGraph() {
        super(null, null);
    }

    public FBRuleInfTVGraph(Reasoner reasoner, Graph schema, long last_timestamp, WindowModel w) {
        super(reasoner, schema);
        this.last_timestamp = last_timestamp;
        this.window = w;
    }


    public FBRuleInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, long last_timestamp, WindowModel w) {
        super(reasoner, rules, schema);
        this.last_timestamp = last_timestamp;
        this.window = w;
    }

    public FBRuleInfTVGraph(Reasoner reasoner, List<Rule> rules, Graph schema, Graph data,
                            long last_timestamp, WindowModel w) {
        super(reasoner, rules, schema, data);
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
    public WindowModel getWindowOperator() {
        return window;
    }

    @Override
    public void setWindowOperator(WindowModel w) {
        this.window = w;
    }
}
