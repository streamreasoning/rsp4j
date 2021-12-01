package org.streamreasoning.rsp4j.csparql2.stream;


import org.streamreasoning.rsp4j.esper.streams.schema.RDFStreamSchema;
import org.apache.jena.graph.Graph;
import org.streamreasoning.rsp4j.api.stream.metadata.SchemaEntry;

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

