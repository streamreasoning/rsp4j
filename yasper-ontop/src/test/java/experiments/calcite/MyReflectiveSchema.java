package experiments.calcite;

import com.google.common.collect.*;
import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.*;
import org.apache.calcite.linq4j.function.Function1;
import org.apache.calcite.linq4j.tree.Expression;
import org.apache.calcite.linq4j.tree.Expressions;
import org.apache.calcite.linq4j.tree.Primitive;
import org.apache.calcite.linq4j.tree.Types;
import org.apache.calcite.rel.RelReferentialConstraint;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.*;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.AbstractTableQueryable;
import org.apache.calcite.schema.impl.ReflectiveFunctionBase;
import org.apache.calcite.util.BuiltInMethod;
import org.apache.calcite.util.Util;

import java.lang.reflect.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by riccardo on 09/09/2017.
 */
public class MyReflectiveSchema
        extends AbstractSchema {
    private final Class clazz;
    private Object target;
    private Map<String, Table> tableMap;
    private Multimap<String, Function> functionMap;

    /**
     * Creates a MyReflectiveSchema.
     *
     * @param target Object whose fields will be sub-objects of the schema
     */
    public MyReflectiveSchema(Object target) {
        super();
        this.clazz = target.getClass();
        this.target = target;
        createTableMap();
    }

    @Override
    public String toString() {
        return "MyReflectiveSchema(target=" + target + ")";
    }

    /**
     * Returns the wrapped object.
     * <p>
     * <p>May not appear to be used, but is used in generated code via
     * {@link org.apache.calcite.util.BuiltInMethod#REFLECTIVE_SCHEMA_GET_TARGET}.
     */
    public Object getTarget() {
        return target;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected Map<String, Table> getTableMap() {
        if (tableMap == null) {
            tableMap = createTableMap();
        }
        return tableMap;
    }

    private Map<String, Table> createTableMap() {
        final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
        for (Field field : clazz.getFields()) {
            final String fieldName = field.getName();
            final Table table = fieldRelation(field);
            if (table == null) {
                continue;
            }
            builder.put(fieldName, table);
        }
        this.tableMap = builder.build();
        // Unique-Key - Foreign-Key
        for (Field field : clazz.getFields()) {
            if (RelReferentialConstraint.class.isAssignableFrom(field.getType())) {
                RelReferentialConstraint rc;
                try {
                    rc = (RelReferentialConstraint) field.get(target);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(
                            "Error while accessing method " + field, e);
                }
                MyReflectiveSchema.FieldTable table =
                        (MyReflectiveSchema.FieldTable) tableMap.get(Util.last(rc.getSourceQualifiedName()));
                assert table != null;
                table.statistic = Statistics.of(
                        ImmutableList.copyOf(
                                Iterables.concat(
                                        table.getStatistic().getReferentialConstraints(),
                                        Collections.singleton(rc))));
            }
        }
        return tableMap;
    }

    @Override
    protected Multimap<String, Function> getFunctionMultimap() {
        if (functionMap == null) {
            functionMap = createFunctionMap();
        }
        return functionMap;
    }

    private Multimap<String, Function> createFunctionMap() {
        final ImmutableMultimap.Builder<String, Function> builder =
                ImmutableMultimap.builder();
        for (Method method : clazz.getMethods()) {
            final String methodName = method.getName();
            if (method.getDeclaringClass() == Object.class
                    || methodName.equals("toString")) {
                continue;
            }
            if (TranslatableTable.class.isAssignableFrom(method.getReturnType())) {
                final TableMacro tableMacro =
                        new MyReflectiveSchema.MethodTableMacro(this, method);
                builder.put(methodName, tableMacro);
            }
        }
        return builder.build();
    }

    /**
     * Returns an expression for the object wrapped by this schema (not the
     * schema itself).
     */
    Expression getTargetExpression(SchemaPlus parentSchema, String name) {
        return Types.castIfNecessary(
                target.getClass(),
                Expressions.call(
                        Schemas.unwrap(
                                getExpression(parentSchema, name),
                                MyReflectiveSchema.class),
                        BuiltInMethod.REFLECTIVE_SCHEMA_GET_TARGET.method));
    }

    /**
     * Returns a table based on a particular method of this schema. If the
     * method is not of the right type to be a relation, returns null.
     */
    private <T> Table fieldRelation(final Field field) {
        final Type elementType = getElementType(field);
        if (elementType == null) {
            return null;
        }
        try {
            Object o = field.get(target);
            return new MyReflectiveSchema.FieldTable<>(field, elementType, toEnumerable(o));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Error while accessing method " + field, e);
        }
    }


    /**
     * Deduces the element type of a collection;
     * same logic as {@link #toEnumerable}
     *
     * @param field
     */
    private static Type getElementType(Field field) {
        Class<?> clazz = field.getType();
        if (clazz.isArray()) {
           return clazz.getComponentType();
        } else if (Iterable.class.isAssignableFrom(clazz)) {
            Type genericFieldType = field.getGenericType();

            if (genericFieldType instanceof ParameterizedType) {
                ParameterizedType aType = (ParameterizedType) genericFieldType;
                Type[] fieldArgTypes = aType.getActualTypeArguments();
                for (Type fieldArgType : fieldArgTypes) {
                    return fieldArgType;
                }
            }
        }
        return Object.class;
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
                    "Cannot convert " + o.getClass() + " into a Enumerable");
    }

    /**
     * Table that is implemented by reading from a Java object.
     */
    private static class ReflectiveTable
            extends AbstractQueryableTable
            implements Table, ScannableTable {
        private final Type elementType;
        private final Enumerable enumerable;

        ReflectiveTable(Type elementType, Enumerable enumerable) {
            super(elementType);
            this.elementType = elementType;
            this.enumerable = enumerable;
        }

        public RelDataType getRowType(RelDataTypeFactory typeFactory) {
            return ((JavaTypeFactory) typeFactory).createType(elementType);
        }

        public Statistic getStatistic() {
            return Statistics.UNKNOWN;
        }

        public Enumerable<Object[]> scan(DataContext root) {
            if (elementType == Object[].class) {
                //noinspection unchecked
                return enumerable;
            } else {
                //noinspection unchecked
                return enumerable.select(new MyReflectiveSchema.FieldSelector((Class) elementType));
            }
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
    }

    /**
     * Factory that creates a schema by instantiating an object and looking at
     * its public fields.
     * <p>
     * <p>The following example instantiates a {@code FoodMart} object as a schema
     * that contains tables called {@code EMPS} and {@code DEPTS} based on the
     * object's fields.
     * <p>
     * <blockquote><pre>
     * schemas: [
     *     {
     *       name: "foodmart",
     *       type: "custom",
     *       factory: "MyReflectiveSchema$Factory",
     *       operand: {
     *         class: "com.acme.FoodMart",
     *         staticMethod: "instance"
     *       }
     *     }
     *   ]
     * &nbsp;
     * class FoodMart {
     *   public static final FoodMart instance() {
     *     return new FoodMart();
     *   }
     * &nbsp;
     *   Employee[] EMPS;
     *   Department[] DEPTS;
     * }</pre></blockquote>
     */
    public static class Factory implements SchemaFactory {
        public Schema create(SchemaPlus parentSchema, String name,
                             Map<String, Object> operand) {
            Class<?> clazz;
            Object target;
            final Object className = operand.get("class");
            if (className != null) {
                try {
                    clazz = Class.forName((String) className);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Error loading class " + className, e);
                }
            } else {
                throw new RuntimeException("Operand 'class' is required");
            }
            final Object methodName = operand.get("staticMethod");
            if (methodName != null) {
                try {
                    //noinspection unchecked
                    Method method = clazz.getMethod((String) methodName);
                    target = method.invoke(null);
                } catch (Exception e) {
                    throw new RuntimeException("Error invoking method " + methodName, e);
                }
            } else {
                try {
                    final Constructor<?> constructor = clazz.getConstructor();
                    target = constructor.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Error instantiating class " + className,
                            e);
                }
            }
            return new MyReflectiveSchema(target);
        }
    }

    /**
     * Table macro based on a Java method.
     */
    private static class MethodTableMacro extends ReflectiveFunctionBase
            implements TableMacro {
        private final MyReflectiveSchema schema;

        MethodTableMacro(MyReflectiveSchema schema, Method method) {
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

    /**
     * Table based on a Java method.
     *
     * @param <T> element type
     */
    private static class FieldTable<T> extends MyReflectiveSchema.ReflectiveTable {
        private final Field field;
        private Statistic statistic;

        FieldTable(Field field, Type elementType, Enumerable<T> enumerable) {
            this(field, elementType, enumerable, Statistics.UNKNOWN);
        }

        FieldTable(Field field, Type elementType, Enumerable<T> enumerable,
                   Statistic statistic) {
            super(elementType, enumerable);
            this.field = field;
            this.statistic = statistic;
        }

        public String toString() {
            return "Relation {method=" + field.getName() + "}";
        }

        @Override
        public Statistic getStatistic() {
            return statistic;
        }

        @Override
        public Expression getExpression(SchemaPlus schema,
                                        String tableName, Class clazz) {
            MyReflectiveSchema unwrap = schema.unwrap(MyReflectiveSchema.class);
            return Expressions.field(
                    unwrap.getTargetExpression(
                            schema.getParentSchema(), schema.getName()), field);
        }
    }

    private static class MethodTable<T> extends MyReflectiveSchema.ReflectiveTable {
        private final Method method;
        private Statistic statistic;

        MethodTable(Method method, Type elementType, Enumerable<T> enumerable) {
            this(method, elementType, enumerable, Statistics.UNKNOWN);
        }

        MethodTable(Method method, Type elementType, Enumerable<T> enumerable,
                    Statistic statistic) {
            super(elementType, enumerable);
            this.method = method;
            this.statistic = statistic;
        }

        public String toString() {
            return "Relation {method=" + method.getName() + "}";
        }

        @Override
        public Statistic getStatistic() {
            return statistic;
        }

        @Override
        public Expression getExpression(SchemaPlus schema,
                                        String tableName, Class clazz) {
            return null;
        }
    }

    /**
     * Function that returns an array of a given object's method values.
     */
    private static class FieldSelector implements Function1<Object, Object[]> {
        private final Field[] fields;

        FieldSelector(Class elementType) {
            this.fields = elementType.getFields();
        }

        public Object[] apply(Object o) {
            try {
                final Object[] objects = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    objects[i] = fields[i].get(o);
                }
                return objects;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}