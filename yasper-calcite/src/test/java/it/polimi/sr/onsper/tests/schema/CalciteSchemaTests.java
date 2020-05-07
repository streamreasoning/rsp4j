package it.polimi.sr.onsper.tests.schema;

import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.unibz.inf.ontop.dbschema.RDBMetadata;
import it.unibz.inf.ontop.dbschema.RDBMetadataExtractionTools;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.polimi.sr.rsp.onsper.DBTablePrinter;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.SDSQuerySchema;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import org.apache.calcite.schema.Schema;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by riccardo on 31/08/2017.
 */
public class CalciteSchemaTests extends CalciteAbstractSchemaTests {


    @Test
    public void singleTableTest() throws SQLException, InterruptedException {
        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
        Statement statement = calciteConnection.createStatement();

        RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata("jdbc:db:");
        RelationID relationID = dbMetadata.getQuotedIDFactory().createRelationID("\"hr\"", "\"emps\"");
        DatabaseRelationDefinition table = dbMetadata.createDatabaseRelation(relationID);

        SchemaEntry empid = new SimpleSchemaEntry("age", "Integer", 1, Types.INTEGER);
        SchemaEntry name = new SimpleSchemaEntry("name", "String", 2, Types.VARCHAR);

        table.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(empid.getID()), empid.getType(), empid.getTypeName(), empid.canNull());
        table.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(name.getID()), name.getType(), name.getTypeName(), name.canNull());

        Relation<Tuple2> emps3 = builder.addTuple("hr", "emps", table);

        Tuple2<Integer, String> renzo_piano = new Tuple2<>(1001, "Renzo Piano");
        Tuple2<Integer, String> tito_boeri = new Tuple2<>(2001, "Tito Boeri");

        emps3.add(renzo_piano);
        emps3.add(tito_boeri);

        Schema build = builder.build();
        rootSchema.add("hr", build);

        ResultSet resultSet =
                statement.executeQuery("select *\n"
                        + "from \"hr\".\"emps\"");


        ResultSetMetaData metaData = resultSet.getMetaData();

        assertTrue(resultSet.next());

        String columnLabel = metaData.getColumnLabel(empid.getIndex());
        assertEquals(columnLabel, empid.getID().replace("\"", ""));
        Object object = resultSet.getObject(columnLabel);
        assertEquals(object, 1001);
        System.out.println(columnLabel + "===" + object.toString());

        columnLabel = metaData.getColumnLabel(name.getIndex());
        assertEquals(columnLabel, name.getID().replace("\"", ""));
        object = resultSet.getObject(columnLabel);
        assertEquals(object, "Renzo Piano");
        System.out.println(columnLabel + "===" + object.toString());

        assertTrue(resultSet.next());

        columnLabel = metaData.getColumnLabel(empid.getIndex());
        assertEquals(columnLabel, empid.getID().replace("\"", ""));
        object = resultSet.getObject(columnLabel);
        assertEquals(object, 2001);
        System.out.println(columnLabel + "===" + object.toString());

        columnLabel = metaData.getColumnLabel(name.getIndex());
        assertEquals(columnLabel, name.getID().replace("\"", ""));
        object = resultSet.getObject(columnLabel);
        assertEquals(object, "Tito Boeri");
        System.out.println(columnLabel + "===" + object.toString());

        resultSet.close();
        statement.close();
    }

    @Test
    public void multipleTableTest() throws SQLException, InterruptedException {
        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
        Statement statement = calciteConnection.createStatement();
        String schema_name = "hr";

        RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata("jdbc:db:");

        //TABLE 1

        RelationID relationID1 = dbMetadata.getQuotedIDFactory().createRelationID("\"" + schema_name + "\"", "\"emps\"");
        DatabaseRelationDefinition table1 = dbMetadata.createDatabaseRelation(relationID1);

        //Create entries
        SchemaEntry empid = new SimpleSchemaEntry("age", "Integer", 1, 4);
        SchemaEntry name = new SimpleSchemaEntry("name", "String", 2, 12);

        //addTuple to metadata
        table1.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(empid.getID()), empid.getType(), empid.getTypeName(), empid.canNull());
        table1.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(name.getID()), name.getType(), name.getTypeName(), name.canNull());

        //addTuple to database builder
        Relation<Tuple> emps1 = builder.addTuple(schema_name, "emps", table1);

        Tuple2<Integer, String> renzo_piano = new Tuple2<>(1001, "Renzo Piano");
        Tuple2<Integer, String> tito_boeri = new Tuple2<>(2001, "Tito Boeri");

        emps1.add(renzo_piano);
        emps1.add(tito_boeri);


        //TABLE 2
        RelationID relationID2 = dbMetadata.getQuotedIDFactory().createRelationID("\"hr\"", "\"users\"");

        DatabaseRelationDefinition table2 = dbMetadata.createDatabaseRelation(relationID2);

        SchemaEntry nick = new SimpleSchemaEntry("nick", "String", 1, 12);
        SchemaEntry email = new SimpleSchemaEntry("email", "String", 2, 12);

        table2.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(nick.getID()), nick.getType(), nick.getTypeName(), nick.canNull());
        table2.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(email.getID()), email.getType(), email.getTypeName(), email.canNull());

        Relation<Tuple> users = builder.addTuple(schema_name, "users", table2);

        Tuple2<String, String> john_doe = new Tuple2<>("jdm", "John Doe");
        Tuple2<String, String> jane_dow = new Tuple2<>("jdf", "Jane Doe");

        users.add(john_doe);
        users.add(jane_dow);


        //build database
        rootSchema.add(schema_name, builder.build());

        ResultSet resultSet =
                statement.executeQuery("select * from \"hr\".\"users\", \"hr\".\"emps\"");

        DBTablePrinter.printResultSet(resultSet);

        resultSet.close();
        statement.close();

    }

    @Test
    public void joinTableTest() throws SQLException, InterruptedException {
        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();
        Statement statement = calciteConnection.createStatement();
        String schema_name = "hr";

        RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata("jdbc:db:");

        //TABLE 1

        RelationID relationID1 = dbMetadata.getQuotedIDFactory().createRelationID("\"" + schema_name + "\"", "\"emps\"");
        DatabaseRelationDefinition table1 = dbMetadata.createDatabaseRelation(relationID1);

        //Create entries
        SchemaEntry empid = new SimpleSchemaEntry("age", "Integer", 1, 4);
        SchemaEntry name = new SimpleSchemaEntry("name", "String", 2, 12);

        //addTuple to metadata
        table1.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(empid.getID()), empid.getType(), empid.getTypeName(), empid.canNull());
        table1.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(name.getID()), name.getType(), name.getTypeName(), name.canNull());

        //addTuple to database builder
        Relation<Tuple> emps1 = builder.addTuple(schema_name, "emps", table1);

        Tuple2<Integer, String> renzo_piano = new Tuple2<>(1001, "Renzo Piano");
        Tuple2<Integer, String> tito_boeri = new Tuple2<>(2001, "Tito Boeri");

        emps1.add(renzo_piano);
        emps1.add(tito_boeri);


        //TABLE 2
        RelationID relationID2 = dbMetadata.getQuotedIDFactory().createRelationID("\"hr\"", "\"users\"");

        DatabaseRelationDefinition table2 = dbMetadata.createDatabaseRelation(relationID2);

        SchemaEntry nick = new SimpleSchemaEntry("key", "Integer", 1, 4);
        SchemaEntry email = new SimpleSchemaEntry("email", "String", 2, 12);

        table2.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(nick.getID()), nick.getType(), nick.getTypeName(), nick.canNull());
        table2.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(email.getID()), email.getType(), email.getTypeName(), email.canNull());

        Relation<Tuple> users = builder.addTuple(schema_name, "users", table2);

        Tuple2<Integer, String> john_doe = new Tuple2<>(1001, "John Doe");
        Tuple2<Integer, String> jane_dow = new Tuple2<>(2002, "Jane Doe");

        users.add(john_doe);
        users.add(jane_dow);

        //build database
        rootSchema.add(schema_name, builder.build());

        ResultSet resultSet =
                statement.executeQuery("SELECT * FROM \"hr\".\"emps\" AS e JOIN \"hr\".\"users\" AS u ON e.\"empid\" = u.\"key\"");

        DBTablePrinter.printResultSet(resultSet);

        resultSet.close();
        statement.close();

    }


}
