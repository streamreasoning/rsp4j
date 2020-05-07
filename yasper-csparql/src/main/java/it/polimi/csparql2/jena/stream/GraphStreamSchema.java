package it.polimi.csparql2.jena.stream;


import it.polimi.jasper.streams.schema.RDFStreamSchema;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
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

    @Override
    public boolean validate(Object o) {
        return true;
    }
}

