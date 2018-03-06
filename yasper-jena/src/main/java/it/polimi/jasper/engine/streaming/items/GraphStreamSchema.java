package it.polimi.jasper.engine.streaming.items;


import it.polimi.yasper.core.stream.schema.SchemaEntry;
import org.apache.jena.graph.Graph;

import java.util.HashSet;
import java.util.Set;

public class GraphStreamSchema extends RDFStreamSchema {

    public GraphStreamSchema() {
        super(Graph.class);
    }


    @Override
    public Set<SchemaEntry> entrySet() {
        return new HashSet<>();
    }
}

