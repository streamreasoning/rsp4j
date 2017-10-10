package experiments.calcite.reflective;

import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.sr.onsper.engine.Relation;
import it.polimi.yasper.core.query.Updatable;
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
import java.util.Collection;
import java.util.Set;

public class StreamClassTableImpl<T> extends AbstractQueryableTable implements Table, ScannableTable, StreamSchema, Relation<T> {

    private final Type elementType;
    private final Enumerable enumerable;
    private final Collection<T> collection;
    private Statistic statistic;

    public StreamClassTableImpl(Class<T> tClass, Collection<T> o, Enumerable enumerable, Statistic statistic) {
        super(tClass);
        this.elementType = tClass;
        this.enumerable = enumerable;
        this.statistic = statistic;
        this.collection = o;
    }

    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        RelDataType type = ((JavaTypeFactory) typeFactory).createType(elementType);
        return type;
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

    @Override
    public Updatable<T> asUpdatable() {
        return this;
    }

    @Override
    public Instantaneous asInstantaneous() {
        return null;
    }

    @Override
    public Collection<T> getCollection() {
        return collection;
    }

    public Relation<T> asRelation() {
        return this;
    }

    @Override
    public void add(T o) {
        collection.add(o);
    }

    @Override
    public void remove(T o) {
        collection.remove(o);
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public boolean isSetSemantics() {
        return (collection instanceof Set);
    }

    @Override
    public void clear() {
        collection.clear();
    }
}
