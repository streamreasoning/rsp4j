package experiments.calcite;

import it.polimi.yasper.core.stream.SchemaEntry;

import java.sql.Types;

public class FieldSchemaEntry implements SchemaEntry {
    private final int index;
    private final int type;
    private final String name;
    private final String type_name;

    @Override
    public String toString() {
        return "FieldSchemaEntry{" +
                "index=" + index +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", type_name='" + type_name + '\'' +
                '}';
    }

    public FieldSchemaEntry(String name, Class<?> type, int index) {
        this.index = index;
        this.name = name;
        this.type_name = type.getSimpleName();
        if (type.equals(String.class))
            this.type = Types.VARCHAR;
        else if (type.equals(Integer.class))
            this.type = Types.INTEGER;
        else if (type.equals(Boolean.class))
            this.type = Types.BOOLEAN;
        else this.type = Types.VARCHAR;

    }

    @Override
    public String getID() {
        return "\"" + this.name + "\"";
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
        return false;
    }

}
