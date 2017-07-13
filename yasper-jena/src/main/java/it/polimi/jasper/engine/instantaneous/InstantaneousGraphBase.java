package it.polimi.jasper.engine.instantaneous;

import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.mem.GraphMem;
import org.apache.jena.rdf.model.Statement;

/**
 * Created by riccardo on 05/07/2017.
 */
public class InstantaneousGraphBase extends GraphMem implements InstantaneousGraph {

    private long last_timestamp;
    private TimeVaryingGraph window;

    public InstantaneousGraphBase() {
        this.last_timestamp = -1;
        this.window = null;
    }

    public InstantaneousGraphBase(long last_timestamp, TimeVaryingGraph window) {
        this.last_timestamp = last_timestamp;
        this.window = window;
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
