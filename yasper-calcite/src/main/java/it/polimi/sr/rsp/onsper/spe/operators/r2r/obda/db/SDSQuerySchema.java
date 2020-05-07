package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.rewriting.OntopTableImpl;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.linq4j.tree.Expressions;
import org.apache.calcite.linq4j.tree.Primitive;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.schema.*;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.util.BuiltInMethod;
import org.jooq.lambda.tuple.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by riccardo on 09/09/2017.
 */
public class SDSQuerySchema extends AbstractSchema {
    private Map<String, Table> tempTableMap;
    private Map<String, Table> tableMap;
    private Map<String, Object> targetMap;
    private Multimap<String, Function> functionMap;

    public SDSQuerySchema(Map tempTableMap, Map tableMap, Map targetMap) {
        super();
        this.tempTableMap = tempTableMap;
        this.targetMap = targetMap;
        this.tableMap = tableMap;
    }

    /**
     * Returns the wrapped object.
     * <p>
     * <p>May not appear to be used, but is used in generated code via
     * {@link BuiltInMethod#REFLECTIVE_SCHEMA_GET_TARGET}.
     */
    public Object getTarget() {
        return targetMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Map<String, Table> getTableMap() {
        if (tableMap == null) {
            final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
            tempTableMap.entrySet().forEach(e -> builder.put(e.getKey(), e.getValue()));
            tableMap = builder.build();
        }
        return tableMap;
    }

//    @Override
//    protected Multimap<String, Function> getFunctionMultimap() {
//        if (functionMap == null) {
//            functionMap = createFunctionMap();
//        }
//        return functionMap;
//    }
//
//    //TODO neat idea, introducing UDF as methods
//    private Multimap<String, Function> createFunctionMap() {
//        final ImmutableMultimap.Builder<String, Function> builder =
//                ImmutableMultimap.builder();
//        for (Method method : clazz.getMethods()) {
//            final String methodName = method.getName();
//            if (method.getDeclaringClass() == Object.class
//                    || methodName.equals("toString")) {
//                continue;
//            }
//            if (TranslatableTable.class.isAssignableFrom(method.getReturnType())) {
//                final TableMacro tableMacro =
//                        new MethodTableMacro(this, method);
//                builder.put(methodName, tableMacro);
//            }
//        }
//        return builder.build();
//    }

    /**
     * Returns an expression for the object wrapped by this schema (not the
     * schema itself).
     */
    public Expression getTargetExpression(SchemaPlus parentSchema, String name) {
        Method method = BuiltInMethod.REFLECTIVE_SCHEMA_GET_TARGET.method;
        Expression expression = getExpression(parentSchema, name);
        return Types.castIfNecessary(
                Map.class,
                Expressions.call(
                        Schemas.unwrap(
                                expression,
                                SDSQuerySchema.class),
                        method));
    }


    public static class Builder {
        private Map<String, Table> tempTableMap;
        private Map<String, Collection> targetMap;

        public Builder() {
            this.tempTableMap = new HashMap<>();
            this.targetMap = new HashMap<>();
        }

       /* public void addTuple(Object o) {
            Class<?> clazz = o.getClass();
            Arrays.stream(clazz.getFields()).forEach(field -> {
                try {
                    String fieldName = field.getName();
                    Object value = field.get(o);
                    List<Object> l = new LinkedList<>();
                    l.addTuple(value);
                    targetMap.put(fieldName, l);
                    Optional<Table> table = Optional.of(new FieldTable(field, getElementType(field), toEnumerable(value)));
                    if (table.isPresent()) {
                        tempTableMap.put(fieldName, table.get());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            Arrays.stream(clazz.getMethods()).forEach(method -> {
                //TODO methods as fieds (getter Only)
            });

            Arrays.stream(clazz.getMethods()).forEach(method -> {
                //TODO methods of Object as UDF
//                final String methodName = method.getName();
//                if (method.getDeclaringClass() != Object.class
//                        && !methodName.equals("toString")) {
//                    if (TranslatableTable.class.isAssignableFrom(method.getReturnType())) {
//                        final TableMacro tableMacro =
//                                new MethodTableMacro(this, method);
//                        builder.put(methodName, tableMacro);
//                    }
//                }
            });
        }*/

        public <T extends Tuple> Relation<T> addTuple(String schemaName, String tableName, DatabaseRelationDefinition table) {
            LinkedList<T> l = new LinkedList<>();
            targetMap.put(tableName, l);
            OntopTableImpl<T> value = new OntopTableImpl<>(getTupleClass(table), schemaName, tableName, table, l, toEnumerable(l), Statistics.UNKNOWN);
            tempTableMap.put(tableName, value);
            return value;
        }

        private Class<? extends Tuple> getTupleClass(DatabaseRelationDefinition table) {
            //todo move into a sort of factory
            switch (table.getAttributes().size()) {
                case 2:
                    return Tuple2.class;
                case 3:
                    return Tuple3.class;
                case 4:
                    return Tuple4.class;
                case 5:
                    return Tuple5.class;
            }
            return null;
        }

/*        public <T> Collection<T> addTuple(String name, Class<T> c) {
            LinkedList<T> o = new LinkedList<>();
            Table table = new StreamClassTableImpl<>(c, o, toEnumerable(o), Statistics.UNKNOWN);
            tempTableMap.put(name, table);
            targetMap.put(name, o);
            return o;
        }

        public <T> Relation<T> add2(String name, Class<T> c) {
            LinkedList<T> l = new LinkedList<>();
            targetMap.put(name, l);
            StreamClassTableImpl<T> value = new StreamClassTableImpl<>(c, l, toEnumerable(l), Statistics.UNKNOWN);
            tempTableMap.put(name, value);
            return value;
        }*/

        //TODO need to extend AbstractQueryableTable to build a table out of a Stream Schema
        public <T> Relation<T> add2(String name, StreamSchema c) {
            return null;
        }

        private Type getElementType(Field field) {
            Class<?> clazz = field.getType();
            if (clazz.isArray()) {
                return clazz.getComponentType();
            } else if (Iterable.class.isAssignableFrom(clazz)) {
                if (field.getGenericType() instanceof ParameterizedType) {
                    for (Type fieldArgType : ((ParameterizedType) field.getGenericType()).getActualTypeArguments()) {
                        return fieldArgType;
                    }
                }
            }
            return Object.class;
        }

        public Schema build() {
            ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
            this.tempTableMap.forEach(builder::put);
            return new SDSQuerySchema(this.tempTableMap, builder.build(), this.targetMap);
        }

        private static Enumerable toEnumerable(final Object o) {
            if (o.getClass().isArray()) {
                if (o instanceof Object[]) {
                    return Linq4j.asEnumerable((Object[]) o);
                } else {
                    return Linq4j.asEnumerable(Primitive.asList(o));
                }
            } else if (o instanceof Iterable) {
                return Linq4j.asEnumerable((Iterable) o);
            } else

                throw new RuntimeException(
                        "Cannot  convert " + o.getClass() + " into a Enumerable");
        }
    }
}