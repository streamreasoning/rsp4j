package experiments.calcite.reflective;

import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class MethodTable<T> extends ObjectStreamTableImpl {
    private final Method method;

    public MethodTable(Method method, Type elementType, Enumerable<T> enumerable) {
        this(method, elementType, enumerable, Statistics.UNKNOWN);
    }

    public MethodTable(Method method, Type elementType, Enumerable<T> enumerable, Statistic statistic) {
        super(elementType, enumerable, statistic);
        this.method = method;
    }

    public String toString() {
        return "Relation {method=" + method.getName() + "}";
    }


    @Override
    public Expression getExpression(SchemaPlus schema,
                                    String tableName, Class clazz) {
        return null;
    }
}
