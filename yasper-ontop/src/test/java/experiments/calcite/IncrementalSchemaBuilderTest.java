package experiments.calcite;

import it.polimi.sr.onsper.query.schema.OntopJavaTypeFactory;
import it.polimi.sr.onsper.query.schema.SDSQuerySchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.CalciteJdbc41Factory;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.Driver;
import org.apache.calcite.schema.SchemaPlus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created by riccardo on 31/08/2017.
 */

//TODO Not working
public class IncrementalSchemaBuilderTest {

    private static SchemaPlus plus;
    private static CalciteSchema rootSchema;
    private static CalciteConnection calciteConnection;

    @Before
    public void init() throws ClassNotFoundException, SQLException {

        CalciteJdbc41Factory factory = new CalciteJdbc41Factory();
        Driver driver = new Driver();
        rootSchema = CalciteSchema.createRootSchema(true);
        plus = rootSchema.plus();
        calciteConnection = factory.newConnection(driver, factory, "jdbc:calcite:", new Properties(), rootSchema, new OntopJavaTypeFactory());
    }


//    @Test
//    public void test1() throws SQLException {
//        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
//
//        List<Employee> emps1 = new ArrayList<>();
//
//        emps1.add(new Employee(100, "Bill"));
//        emps1.add(new Employee(200, "Eric"));
//        emps1.add(new Employee(150, "Sebastian"));
//
//        HrList hr1 = new HrList(emps1);
//
//        builder.add(hr1);
//
//        rootSchema.add("hr", builder.build());
//
//        Statement statement = calciteConnection.createStatement();
//        ResultSet resultSet = statement
//                .executeQuery("select *\n"
//                        + "from \"hr\".\"emps1\"");
//        StringBuilder buf = new StringBuilder();
//        printResults(resultSet);
//        resultSet.close();
//        statement.close();
//
//        assertEquals("", "");
//
//    }
//
//    @Test
//    public void test1Update() throws SQLException {
//        Statement statement = calciteConnection.createStatement();
//
//        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
//
//        List<Employee> emps1 = new ArrayList<>();
//
//        emps1.add(new Employee(100, "Bill"));
//        emps1.add(new Employee(200, "Eric"));
//        emps1.add(new Employee(150, "Sebastian"));
//
//        HrList hr1 = new HrList(emps1);
//
//        builder.add(hr1);
//
//        rootSchema.add("hr", builder.build());
//
//        ResultSet resultSet =
//                statement.executeQuery("select *\n"
//                        + "from \"hr\".\"emps1\"");
//        StringBuilder buf = new StringBuilder();
//        printResults(resultSet);
//        resultSet.close();
//        statement.close();
//
//        //UPDATE
//        emps1.add(new Employee(420, "Riccardo"));
//
//        resultSet =
//                statement.executeQuery("select *\n"
//                        + "from \"hr\".\"emps1\"");
//        printResults(resultSet);
//        resultSet.close();
//        statement.close();
//
//        assertEquals("", "");
//    }

//    @Test
//    public void test2() throws SQLException {
//        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
//        Statement statement = calciteConnection.createStatement();
//
//        List<Engineer> emps2 = new ArrayList<>();
//
//        emps2.add(new Engineer(1001, "Claude Shannon"));
//        emps2.add(new Engineer(2001, "Alan Turing"));
//        emps2.add(new Engineer(1501, "Luke Skywalker"));
//
//        En hr2 = new En(emps2.toArray(new Engineer[emps2.size()]));
//
//        builder.add(hr2);
//
//        rootSchema.add("hr", builder.build());
//
//        ResultSet resultSet =
//                statement.executeQuery("select *\n"
//                        + "from \"hr\".\"emps2\"");
//        StringBuilder buf = new StringBuilder();
//        printResults(resultSet);
//        resultSet.close();
//
//        assertEquals("", "");
//
//    }

    @Test
    public void test3() throws SQLException {
        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
        Statement statement = calciteConnection.createStatement();


        Collection<Architect> emps3 =null;//builder.add("emps3", Architect.class); //esper like

        rootSchema.add("hr", builder.build());

        emps3.add(new Architect(1001, "Renzo Piano"));
        emps3.add(new Architect(2001, "Tito Boeri"));

        ResultSet resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr\".\"emps3\"");
        printResults(resultSet);
        resultSet.close();
        statement.close();

        assertEquals("", "");

    }

    @Test
    public void test3Update() throws SQLException {
        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
        Statement statement = calciteConnection.createStatement();

        Collection<Architect> emps3 = null;//builder.add("emps3", Architect.class); //esper like

        emps3.add(new Architect(1001, "Renzo Piano"));
        emps3.add(new Architect(2001, "Tito Boeri"));

        rootSchema.add("hr", builder.build());

        ResultSet resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr\".\"emps3\"");
        StringBuilder buf = new StringBuilder();
        printResults(resultSet);
        resultSet.close();
        statement.close();

        emps3.add(new Architect(3001, "Tito2 Boeri2"));

        resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr\".\"emps3\"");
        printResults(resultSet);
        resultSet.close();
        statement.close();

        assertEquals("", "");

    }

    @After
    public void clean() throws SQLException {
        calciteConnection.close();

    }


        private void printResults(ResultSet resultSet) throws SQLException {
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    int columnType = metaData.getColumnType(i);
                    String columnLabel = metaData.getColumnLabel(i);
                    Object object = resultSet.getObject(columnLabel);
                    System.err.println(columnLabel + "  " + columnType + " " + object.toString());
                }
            }
    }


}
