package org.streamreasoning.rsp4j.esper.streams.schema;


import org.streamreasoning.rsp4j.api.stream.metadata.StreamSchema;

public abstract class RDFStreamSchema implements StreamSchema {

    protected final Class<?> type;

    public RDFStreamSchema(Class<?> clazz) {
        this.type = clazz;
    }

    @Override
    public Class getType() {
        return type;
    }

}
