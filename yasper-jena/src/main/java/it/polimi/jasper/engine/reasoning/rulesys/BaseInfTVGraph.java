package it.polimi.jasper.engine.reasoning.rulesys;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.reasoner.*;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 * Created by riccardo on 06/07/2017.
 */
public class BaseInfTVGraph extends BaseInfGraph implements InfGraph {
    private Graph window;

    /**
     * Constructor
     *
     * @param data     the raw data file to be augmented with entailments
     * @param reasoner the RSPEngineImpl, with associated tbox data, whose find interface
     */
    public BaseInfTVGraph(Graph data, Reasoner reasoner, Graph w) {
        super(data, reasoner);
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


//    @Override
//    public void addObservable(Object o) {
//        if (o instanceof Triple) {
//            addObservable((Triple) o);
//        } else if (o instanceof GraphItem) {
//            GraphUtil.addInto(this, (GraphItem) o);
//        }
//    }
//
//    @Override
//    public void remove(Object o) {
//        if (o instanceof Statement) {
//            Statement s = (Statement) o;
//            remove(s.getSubject().asNode(), s.getPredicate().asNode(), s.getObject().asNode());
//        } else if (o instanceof GraphItem) {
//            GraphUtil.deleteFrom(this, (GraphItem) o);
//        }
//    }
//
//    @Override
//    public boolean contains(Object o) {
//        return false;
//    }
//
//    @Override
//    public boolean isSetSemantics() {
//        return false;
//    }
}
