package it.polimi.jasper.engine.reasoning.rulesys;

import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import org.apache.jena.reasoner.rulesys.impl.LPRuleStore;

/**
 * Created by riccardo on 05/07/2017.
 */
public class LPBackwardRuleInfTvGraph extends LPBackwardRuleInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private WindowOperator window;

    public LPBackwardRuleInfTvGraph(Reasoner reasoner, LPRuleStore ruleStore, Graph data, Graph schema, long last_timestamp, WindowOperator w) {
        super(reasoner, ruleStore, data, schema);
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
