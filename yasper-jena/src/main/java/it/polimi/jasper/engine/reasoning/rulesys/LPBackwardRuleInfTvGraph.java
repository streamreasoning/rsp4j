package it.polimi.jasper.engine.reasoning.rulesys;

import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.LPBackwardRuleInfGraph;
import org.apache.jena.reasoner.rulesys.impl.LPRuleStore;

/**
 * Created by riccardo on 05/07/2017.
 */
public class LPBackwardRuleInfTvGraph extends LPBackwardRuleInfGraph implements TimeVaryingInfGraph {

    private long last_timestamp;
    private TimeVaryingGraph window;

    public LPBackwardRuleInfTvGraph(Reasoner reasoner, LPRuleStore ruleStore, Graph data, Graph schema, long last_timestamp, TimeVaryingGraph w) {
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
    public TimeVaryingGraph getWindowOperator() {
        return window;
    }

    @Override
    public void setWindowOperator(TimeVaryingGraph w) {
        this.window = w;
    }


    @Override
    public void addContent(Object o) {
        if (o instanceof Triple) {
            add((Triple) o);
        } else if (o instanceof Graph) {
            GraphUtil.addInto(this, (Graph) o);
        }
    }

    @Override
    public void removeContent(Object o) {
        if (o instanceof Statement) {
            Statement s = (Statement) o;
            remove(s.getSubject().asNode(), s.getPredicate().asNode(), s.getObject().asNode());
        } else if (o instanceof Graph) {
            GraphUtil.deleteFrom(this, (Graph) o);
        }
    }
}
