package experiments.calcite;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riccardo on 31/08/2017.
 */
public class CalciteInMemory {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        List<Employee> emps = new ArrayList<>();

        emps.add(new Employee(100, "Bill"));
        emps.add(new Employee(200, "Eric"));
        emps.add(new Employee(150, "Sebastian"));

        Hr hr1 = new Hr(emps.toArray(new Employee[emps.size()]));
        HrList hr2 = new HrList(emps);


        Class.forName("org.apache.calcite.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection("jdbc:calcite:");
        CalciteConnection calciteConnection =
                connection.unwrap(CalciteConnection.class);

        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        MyReflectiveSchema hrSchema1 = new MyReflectiveSchema(hr1);
        System.out.println(hrSchema1.isMutable());
        MyReflectiveSchema hrSchema2 = new MyReflectiveSchema(hr2);
        System.out.println(hrSchema2.isMutable());
        rootSchema.add("hr1", hrSchema1);
        rootSchema.add("hr2", hrSchema2);

        Statement statement = connection.createStatement();
        ResultSet resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr1\".\"emps1\"");
        StringBuilder buf = new StringBuilder();
        while (resultSet.next()) {
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                Object object = resultSet.getObject(i);
                buf.append(i > 1 ? "; " : "")
                        .append(resultSet.getMetaData().getColumnLabel(i))
                        .append("=")
                        .append(object);
            }
            System.out.println(buf.toString());
            buf.setLength(0);
        }
        resultSet.close();
        statement.close();


        resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr2\".\"emps2\"");
        buf = new StringBuilder();
        while (resultSet.next()) {
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                Object object = resultSet.getObject(i);
                buf.append(i > 1 ? "; " : "")
                        .append(resultSet.getMetaData().getColumnLabel(i))
                        .append("=")
                        .append(object);
            }
            System.out.println(buf.toString());
            buf.setLength(0);
        }
        resultSet.close();

        //UPDATE
        emps.add(new Employee(300, "Riccardo"));

        resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr2\".\"emps2\"");
        buf = new StringBuilder();
        while (resultSet.next()) {
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                Object object = resultSet.getObject(i);
                buf.append(i > 1 ? "; " : "")
                        .append(resultSet.getMetaData().getColumnLabel(i))
                        .append("=")
                        .append(object);
            }
            System.out.println(buf.toString());
            buf.setLength(0);
        }
        resultSet.close();
        statement.close();

        connection.close();
    }


}
