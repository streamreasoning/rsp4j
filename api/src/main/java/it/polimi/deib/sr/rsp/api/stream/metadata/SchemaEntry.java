package it.polimi.deib.sr.rsp.api.stream.metadata;

public interface SchemaEntry {

    String getID();

    String getTypeName();

    int getIndex();

    int getType();

    boolean canNull();

}
