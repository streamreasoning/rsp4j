package org.streamreasoning.rsp4j.api.stream.metadata;

public interface SchemaEntry {

    String getID();

    String getTypeName();

    int getIndex();

    int getType();

    boolean canNull();

}
