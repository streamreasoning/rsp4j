package it.polimi.jasper.engine.stream.items;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import it.polimi.yasper.core.stream.StreamSchema;
import org.apache.jena.graph.Triple;

import java.lang.reflect.Type;

public abstract class RDFStreamSchema implements StreamSchema {

    protected final Class<?> type;

    public RDFStreamSchema(Class<?> clazz) {
        this.type = clazz;
    }

    @Override
    public Type getType() {
        return type;
    }

    public static class GraphStreamSchema extends RDFStreamSchema {

        public GraphStreamSchema() {
            super(Graph.class);
        }


    }

    public static class TripleStreamSchema extends RDFStreamSchema {
        public TripleStreamSchema() {
            super(Triple.class);
        }
    }

}
