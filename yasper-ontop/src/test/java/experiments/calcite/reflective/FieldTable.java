package experiments.calcite.reflective;

import it.polimi.sr.onsper.query.schema.SDSQuerySchema;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.linq4j.tree.Expressions;
import org.apache.calcite.linq4j.tree.MethodCallExpression;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Statistic;
import org.apache.calcite.schema.Statistics;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

//This class was meaningful when the schema was using
// a pojo to contain the collections
public class FieldTable extends ObjectStreamTableImpl {
    private final Field field;

    FieldTable(Field field, Type elementType, Enumerable enumerable) {
        this(field, elementType, enumerable, Statistics.UNKNOWN);
    }

    FieldTable(Field field, Type elementType, Enumerable enumerable,
               Statistic statistic) {
        super(elementType, enumerable, statistic);
        this.field = field;
    }

    public String toString() {
        return "Relation {method=" + field.getName() + "}";
    }


    @Override
    public Expression getExpression(SchemaPlus schema,
                                    String tableName, Class clazz) {
        SDSQuerySchema unwrap = schema.unwrap(SDSQuerySchema.class);

        try {
            Expression targetExpression = unwrap.getTargetExpression(
                    schema.getParentSchema(), schema.getName());
            Class<Map> mapClass = Map.class;
            MethodCallExpression get = Expressions.call(
                    targetExpression, mapClass.getMethod("get", Object.class), Expressions.constant(field.getName()));
            Expression expression = Types.castIfNecessary(field.getType(), get);
            return expression;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }


}