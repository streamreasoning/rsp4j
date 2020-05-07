package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.rewriting;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.ReflectiveSchemaEntryFactory;
import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.linq4j.function.Function1;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.*;
import org.apache.calcite.schema.impl.AbstractTableQueryable;

import java.lang.reflect.Field;
import java.util.*;

public class OntopTableImpl<T> extends AbstractQueryableTable implements Table, ScannableTable, StreamSchema<T>, Relation<T> {

    private final DatabaseRelationDefinition dbReldef;
    private final Enumerable enumerable;
    private final Collection<T> collection;
    private final String tableName;
    private final String schemaName;
    private final Class clazz;
    private final List<SchemaEntry> entries;
    private Statistic statistic;
    private Map<String, Integer> attributeIDs;

    public OntopTableImpl(Class clazz, String schemaName, String tableName, DatabaseRelationDefinition rel, Collection o, Enumerable enumerable, Statistic statistic) {
        super(clazz);
        this.clazz = clazz;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.dbReldef = rel;
        this.enumerable = enumerable;
        this.statistic = statistic;
        this.collection = o;

        attributeIDs = new HashMap<>();

        rel.getAttributes().forEach(a -> attributeIDs.put(a.getID().getName().replace("\"", ""), a.getIndex()));

        this.entries = ReflectiveSchemaEntryFactory.create(clazz);
    }

    public RelDataType getRowType(RelDataTypeFactory typeFactory) {
        //Using the DatabaseRelationDefinition it constructs a view over a stream of
        //Tuple whose cardinality is exactly the same.
        //This allows the system to query a da structure whose name are actually different from
        //the actualy fiedls (i.e. v1...vn). Accessing them w.r.t. the position

        return ((OntopJavaTypeFactory) typeFactory).createStructType(dbReldef);
    }

    public Statistic getStatistic() {
        return statistic != null ? statistic : Statistics.UNKNOWN;
    }

    public Enumerable<Object[]> scan(DataContext root) {
        //NOTE This is still the case where the data on the stream are objects
        //noinspection unchecked
        return enumerable.select((Function1<Object, Object[]>) row -> {
            Object[] objects = new Object[dbReldef.getAttributes().size()];
            Arrays.stream(row.getClass().getFields()).forEach(field -> {
                if (attributeIDs.containsKey(field.getName())) {
                    try {
                        Integer attributeIndex = getAttributeIndex(field);
                        System.out.println(field.getName() + " " + attributeIndex);

                        objects[attributeIndex] = field.get(row);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
            return objects;
        });
    }

    private Integer getAttributeIndex(Field field) {
        return attributeIDs.get(field.getName());
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
    public Class getType() {
        return null;
    }

    @Override
    public Set<SchemaEntry> entrySet() {
        return null;
    }

    @Override
    public boolean validate(T t) {
        return true;
    }

    @Override
    public Collection<T> getCollection() {
        return collection;
    }

    @Override
    public void add(T renzo_piano) {
        collection.add(renzo_piano);
    }

    @Override
    public void remove(T o) {
        collection.remove(o);
    }

    @Override
    public void clear() {
        collection.clear();
    }

    public Relation<T> asRelation() {
        return this;
    }

    @Override
    public String toString() {
        return "OntopTableImpl{" +
                "dbReldef=" + dbReldef +
                ", enumerable=" + enumerable +
                ", collection=" + Objects.toString(collection) +
                ", tableName='" + tableName + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", clazz=" + clazz +
                ", entries=" + entries +
                ", statistic=" + statistic +
                ", attributeIDs=" + attributeIDs +
                '}';
    }
}
