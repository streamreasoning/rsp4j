package experiments.calcite;


import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.*;

/**
 * Created by riccardo on 31/08/2017.
 */
public class CalciteMemoryOld {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("org.apache.calcite.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:calcite:");
        CalciteConnection calciteConnection =
                connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        ReflectiveSchema hrSchema = new ReflectiveSchema(new CalciteMemoryOld.Hr());
        rootSchema.add("hr", hrSchema);
        Statement statement = connection.createStatement();
        ResultSet resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr\".\"emps\"");
        final StringBuilder buf = new StringBuilder();
        while (resultSet.next()) {
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                buf.append(i > 1 ? "; " : "")
                        .append(resultSet.getMetaData().getColumnLabel(i))
                        .append("=")
                        .append(resultSet.getObject(i));
            }
            System.out.println(buf.toString());
            buf.setLength(0);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }

    /**
     * Object that will be used via reflection to create the "hr" schema.
     */
    public static class Hr {
        public final Employee[] emps = {
                new CalciteMemoryOld.Employee(100, "Bill"),
                new CalciteMemoryOld.Employee(200, "Eric"),
                new CalciteMemoryOld.Employee(150, "Sebastian"),
        };
    }

    /**
     * Object that will be used via reflection to create the "emps" table.
     */
    public static class Employee {
        public final int empid;
        public final String name;

        public Employee(int empid, String name) {
            this.empid = empid;
            this.name = name;
        }
    }
}
