package it.polimi.jasper.engine.instantaneous;

import lombok.Setter;
import org.apache.jena.mem.GraphMem;

/**
 * Created by riccardo on 05/07/2017.
 */
public class GraphBase extends GraphMem implements JenaGraph {

    @Setter
    public long timestamp;

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean isSetSemantics() {
        return false;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}