package experiments.calcite.reflective;

import org.apache.calcite.linq4j.function.Function1;

import java.lang.reflect.Field;

public class FieldSelector implements Function1<Object, Object[]> {
    private final Field[] columns;

    public FieldSelector(Class elementType) {
        this.columns = elementType.getFields();
    }

    public Object[] apply(Object row) {
        try {
            Object[] values = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) {
                values[i] = columns[i].get(row);
            }
            //
            return values;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
