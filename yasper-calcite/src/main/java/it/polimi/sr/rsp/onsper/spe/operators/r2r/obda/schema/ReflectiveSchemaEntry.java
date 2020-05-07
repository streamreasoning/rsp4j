package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema;


import it.polimi.yasper.core.stream.metadata.SchemaEntry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;


public class ReflectiveSchemaEntry implements SchemaEntry {

    private final int index;
    private final boolean nullable;
    private final String type_name;
    private final String name;
    private final int type;

    public ReflectiveSchemaEntry(Method getter, int index, boolean nullable, int type) {
        this.index = index;
        this.nullable = nullable;
        this.type_name = getter.getReturnType().getSimpleName();
        this.name = "\"" + getter.getName().replace("get", "").toLowerCase() + "\"";
        this.type = type;
    }

    public ReflectiveSchemaEntry(Field field, int index, boolean nullable, int type) {
        this.index = index;
        this.nullable = nullable;
        this.type_name = field.getType().getSimpleName();
        this.name = "\"" + field.getName().toLowerCase() + "\"";
        this.type = type;
    }


    @Override
    public String getID() {
        return name;
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

    @Override
    public String toString() {
        return "ReflectiveSchemaEntry{" +
                "index=" + index +
                ", nullable=" + nullable +
                ", type_name='" + type_name + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReflectiveSchemaEntry that = (ReflectiveSchemaEntry) o;
        return index == that.index &&
                nullable == that.nullable &&
                type == that.type &&
                Objects.equals(type_name, that.type_name) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, nullable, type_name, name, type);
    }
}
