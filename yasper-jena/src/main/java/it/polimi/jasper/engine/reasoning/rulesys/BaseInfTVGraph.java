package it.polimi.jasper.engine.reasoning.rulesys;

import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.reasoner.BaseInfGraph;
import org.apache.jena.reasoner.Finder;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.TriplePattern;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 * Created by riccardo on 06/07/2017.
 */
public class BaseInfTVGraph extends BaseInfGraph implements TimeVaryingInfGraph {
    private long last_timestamp;
    private TimeVaryingGraph window;

    /**
     * Constructor
     *
     * @param data     the raw data file to be augmented with entailments
     * @param reasoner the engine, with associated tbox data, whose find interface
     */
    public BaseInfTVGraph(Graph data, Reasoner reasoner, long timestamp, TimeVaryingGraph w) {
        super(data, reasoner);
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
    public ExtendedIterator<Triple> findWithContinuation(TriplePattern pattern, Finder continuation) {
        return null;
    }

    @Override
    public Graph getSchemaGraph() {
        return null;
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
