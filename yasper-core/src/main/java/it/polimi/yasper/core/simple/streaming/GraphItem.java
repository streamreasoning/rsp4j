package it.polimi.yasper.core.simple.streaming;

import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.rspql.Item;
import it.polimi.yasper.core.rspql.Updatable;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface GraphItem extends Updatable<Object>, Instantaneous, Graph, Item {

    default void add(Object o) {
        if (o instanceof Triple) {
            add((Triple) o);
        } else if (o instanceof Graph) {
            ((Graph) o).stream().forEach(this::add);
        }
    }

    @Override
    default void remove(Object o) {
        if (o instanceof Triple) {
            remove(o);
        } else if (o instanceof Graph) {
            ((Graph) o).stream().forEach(this::remove);
        }
    }

    @Override
    default GraphItem asUpdatable() {
        return this;
    }
}
