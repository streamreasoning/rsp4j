package it.polimi.yasper.core.stream;

public interface SchemaEntry {
    String getID();

    String getTypeName();

    int getIndex();

    int getType();

    boolean canNull();
}
