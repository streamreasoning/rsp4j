package it.polimi.jasper.streams.schema;


import it.polimi.yasper.core.stream.metadata.StreamSchema;

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
