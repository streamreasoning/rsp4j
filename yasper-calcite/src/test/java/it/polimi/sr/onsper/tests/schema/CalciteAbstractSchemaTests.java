package it.polimi.sr.onsper.tests.schema;

import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.rewriting.OntopJavaTypeFactory;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.CalciteJdbc41Factory;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.Driver;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by riccardo on 31/08/2017.
 */
public abstract class CalciteAbstractSchemaTests {

    protected static CalciteSchema rootSchema;
    protected static CalciteConnection calciteConnection;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        CalciteJdbc41Factory factory = new CalciteJdbc41Factory();
        rootSchema = CalciteSchema.createRootSchema(true);
        calciteConnection = factory.newConnection(new Driver(), factory, "jdbc:db:", new Properties(), rootSchema, new OntopJavaTypeFactory());
    }


    @After
    public void clean() throws SQLException {
        calciteConnection.close();

    }

}
