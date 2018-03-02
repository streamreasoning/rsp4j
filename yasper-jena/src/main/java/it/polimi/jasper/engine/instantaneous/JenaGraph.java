package it.polimi.jasper.engine.instantaneous;

import it.polimi.yasper.core.rspql.Item;
import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.rspql.Updatable;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Statement;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface JenaGraph extends Updatable<Object>, Instantaneous, Graph, Item {

    default void add(Object o) {
        if (o instanceof Triple) {
            add((Triple) o);
        } else if (o instanceof Graph) {
            GraphUtil.addInto(this, (Graph) o);
        }
    }

    @Override
    default void remove(Object o) {
        if (o instanceof Statement) {
            Statement s = (Statement) o;
            remove(s.asTriple());
        } else if (o instanceof Triple) {
            remove(o);
        } else if (o instanceof Graph) {
            GraphUtil.deleteFrom(this, (Graph) o);
        }
    }

    @Override
    default JenaGraph asInstantaneous() {
        return this;
    }

    @Override
    default JenaGraph asUpdatable() {
        return this;
    }
}
