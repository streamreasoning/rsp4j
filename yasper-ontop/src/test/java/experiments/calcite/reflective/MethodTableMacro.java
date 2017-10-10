package experiments.calcite.reflective;

import it.polimi.sr.onsper.query.schema.SDSQuerySchema;
import org.apache.calcite.schema.TableMacro;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.ReflectiveFunctionBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodTableMacro
        extends ReflectiveFunctionBase
        implements TableMacro {
    private final SDSQuerySchema schema;

    MethodTableMacro(SDSQuerySchema schema, Method method) {
        super(method);
        this.schema = schema;
        assert TranslatableTable.class.isAssignableFrom(method.getReturnType())
                : "Method should return TranslatableTable so the macro can be "
                + "expanded";
    }

    public String toString() {
        return "Member {method=" + method + "}";
    }

    public TranslatableTable apply(final List<Object> arguments) {
        try {
            final Object o = method.invoke(schema.getTarget(), arguments.toArray());
            return (TranslatableTable) o;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
