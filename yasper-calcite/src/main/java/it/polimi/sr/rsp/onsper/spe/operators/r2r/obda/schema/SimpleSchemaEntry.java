package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema;

import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleSchemaEntry implements SchemaEntry {

    private final String id;
    private final String type_name;
    private final int index;
    private final int type;
    private final boolean nullable;

    public SimpleSchemaEntry(String id, String type_name, int index, int type) {
        this("\"" + id + "\"", type_name, index, type, false);
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getTypeName() {
        return type_name;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public boolean canNull() {
        return nullable;
    }
}
