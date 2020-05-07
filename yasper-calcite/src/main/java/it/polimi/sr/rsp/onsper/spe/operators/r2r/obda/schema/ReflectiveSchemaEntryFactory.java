package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema;


import it.polimi.yasper.core.stream.metadata.SchemaEntry;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReflectiveSchemaEntryFactory {

    private static int index;

    public static List<SchemaEntry> create(Class<?> personClass) {

        index = 0;
        List<SchemaEntry> entries = new ArrayList<>();

        Arrays.stream(personClass.getMethods())
                .filter(m -> m.getName().contains("get"))
                .filter(m -> !m.getName().toLowerCase().contains("class"))
                .map(method -> getSe(method, getType(method.getReturnType()))).forEach(entries::add);


        Arrays.stream(personClass.getFields())
                .map(field -> getSe(field, getType(field.getType()))).forEach(entries::add);

        return entries;
    }

    private static int getType(Class<?> clazz) {
        if (Integer.class.equals(clazz) || int.class.equals(clazz))
            return Types.INTEGER;
        if (Boolean.class.equals(clazz) || boolean.class.equals(clazz))
            return Types.BOOLEAN;
        if (Long.class.equals(clazz) || long.class.equals(clazz))
            return Types.BIGINT;
        if (Double.class.equals(clazz) || double.class.equals(clazz))
            return Types.DOUBLE;
        if (Float.class.equals(clazz) || float.class.equals(clazz))
            return Types.FLOAT;
        if (Date.class.equals(clazz) || java.sql.Date.class.equals(clazz))
            return Types.DATE;
        if (Timestamp.class.equals(clazz))
            return Types.TIME;
        if (String.class.equals(clazz))
            return Types.VARCHAR;
        return Types.VARCHAR;
    }

    private static ReflectiveSchemaEntry getSe(Field field, int integer) {
        return new ReflectiveSchemaEntry(field, index++, false, integer);
    }

    private static ReflectiveSchemaEntry getSe(Method method, int integer) {
        return new ReflectiveSchemaEntry(method, index++, false, integer);
    }
}
