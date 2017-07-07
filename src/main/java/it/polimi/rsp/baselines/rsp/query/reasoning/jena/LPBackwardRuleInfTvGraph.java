package it.polimi.rsp.baselines.rsp.query.reasoning.jena;

import it.polimi.rsp.baselines.rsp.query.reasoning.TimeVaryingInfGraph;
import it.polimi.rsp.baselines.rsp.sds.windows.WindowModel;
import org.apache.jena.graph.Graph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import org.apache.jena.reasoner.rulesys.impl.LPRuleStore;

/**
 * Created by riccardo on 05/07/2017.
 */
public class LPBackwardRuleInfTvGraph extends LPBackwardRuleInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private WindowModel window;

    public LPBackwardRuleInfTvGraph(Reasoner reasoner, LPRuleStore ruleStore, Graph data, Graph schema, long last_timestamp, WindowModel w) {
        super(reasoner, ruleStore, data, schema);
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
