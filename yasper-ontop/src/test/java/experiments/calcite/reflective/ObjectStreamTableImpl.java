package experiments.calcite.reflective;

import it.polimi.yasper.core.stream.SchemaEntry;
import it.polimi.yasper.core.stream.StreamSchema;
import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.*;
import org.apache.calcite.schema.impl.AbstractTableQueryable;

import java.lang.reflect.Type;
import java.util.Set;

public class ObjectStreamTableImpl extends AbstractQueryableTable
        implements Table, ScannableTable, StreamSchema {
    private final Type elementType;
    private final Enumerable enumerable;
    private Statistic statistic;

    public ObjectStreamTableImpl(Type elementType, Enumerable enumerable, Statistic statistic) {
        super(elementType);
        this.elementType = elementType;
        this.enumerable = enumerable;
        this.statistic = statistic;
    }

    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        return ((JavaTypeFactory) typeFactory).createType(elementType);
    }

    public Statistic getStatistic() {
        return statistic != null ? statistic : Statistics.UNKNOWN;
    }

    public Enumerable<Object[]> scan(DataContext root) {
        if (elementType == Object[].class) {
            //noinspection unchecked
            return enumerable;
        } else {
            //noinspection unchecked
            return enumerable.select(new FieldSelector((Class) elementType));
        }
    }

    public void setStatistic(Statistic s) {
        this.statistic = s;
    }

    public <T> Queryable<T> asQueryable(QueryProvider queryProvider,
                                        SchemaPlus schema, String tableName) {
        return new AbstractTableQueryable<T>(queryProvider, schema, this,
                tableName) {
            @SuppressWarnings("unchecked")
            public Enumerator<T> enumerator() {
                return (Enumerator<T>) enumerable.enumerator();
            }
        };
    }

    @Override
    public Set<SchemaEntry> entrySet() {
        return null;
    }
}