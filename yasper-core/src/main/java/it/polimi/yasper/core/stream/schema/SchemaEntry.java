package it.polimi.yasper.core.stream.schema;

public interface SchemaEntry {

    String getID();

    String getTypeName();

    int getIndex();

    int getType();

    boolean canNull();

}
