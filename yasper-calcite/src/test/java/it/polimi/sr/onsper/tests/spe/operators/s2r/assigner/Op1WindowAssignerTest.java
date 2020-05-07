package it.polimi.sr.onsper.tests.spe.operators.s2r.assigner;

import it.polimi.sr.onsper.tests.schema.CalciteAbstractSchemaTests;
import it.polimi.yasper.core.RDFUtils;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.secret.report.ReportImpl;
import it.polimi.yasper.core.secret.report.strategies.OnWindowClose;
import it.polimi.yasper.core.secret.time.TimeImpl;
import it.polimi.yasper.core.stream.metadata.SchemaEntry;
import it.polimi.yasper.core.stream.metadata.StreamSchema;
import it.unibz.inf.ontop.dbschema.DatabaseRelationDefinition;
import it.unibz.inf.ontop.dbschema.RDBMetadata;
import it.unibz.inf.ontop.dbschema.RDBMetadataExtractionTools;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.polimi.sr.rsp.onsper.DBTablePrinter;
import it.polimi.sr.rsp.onsper.rspql.VirtualSDS;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.Relation;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.db.SDSQuerySchema;
import it.polimi.sr.rsp.onsper.spe.operators.r2r.obda.schema.SimpleSchemaEntry;
import it.polimi.sr.rsp.onsper.spe.operators.s2r.assigner.VCSPARQLWindowAssigner;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.schema.Schema;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class Op1WindowAssignerTest extends CalciteAbstractSchemaTests {

    @Test
    public void test() throws SQLException {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        int scope = 0;

        String query_id = "query1";
        String window_uri = "win1";
        String stream_uri = "stream1";

        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        SDSQuerySchema.Builder builder = new SDSQuerySchema.Builder();

        SchemaEntry empid = new SimpleSchemaEntry("timestamp", "Integer", 1, Types.INTEGER);
        SchemaEntry name = new SimpleSchemaEntry("observation_id", "String", 2, Types.VARCHAR);

        Set<SchemaEntry> entries = new HashSet<>();

        entries.add(empid);
        entries.add(name);

        StreamSchema<SchemaEntry> schema = new StreamSchema<SchemaEntry>() {
            @Override
            public Set<SchemaEntry> entrySet() {
                return entries;
            }

            @Override
            public boolean validate(SchemaEntry schemaEntry) {
                return entries.contains(schemaEntry);
            }
        };

        RDBMetadata dbMetadata = RDBMetadataExtractionTools.createDummyMetadata("jdbc:db:");
        RelationID relationID = dbMetadata.getQuotedIDFactory().createRelationID("\"" + stream_uri + "\"", "\"" + window_uri + "\"");
        DatabaseRelationDefinition table = dbMetadata.createDatabaseRelation(relationID);

        table.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(empid.getID()), empid.getType(), empid.getTypeName(), empid.canNull());
        table.addAttribute(dbMetadata.getQuotedIDFactory().createAttributeID(name.getID()), name.getType(), name.getTypeName(), name.canNull());


        Relation<Tuple> relation = builder.addTuple(stream_uri, window_uri, table);

        Statement statement = calciteConnection.createStatement();

        Schema build = builder.build();
        CalciteSchema add = rootSchema.add(query_id, build);

        Graph mapping = RDFUtils.createGraph();

        Assigner<Tuple, Relation<Tuple>> windowAssigner = new VCSPARQLWindowAssigner(RDFUtils.createIRI(window_uri), 2000, 2000, scope, scope, time, schema, mapping, relation, tick, report, report_grain);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        SDS sds = new VirtualSDS();

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Relation<Tuple>> timeVarying = windowAssigner.set(sds);

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;

            timeVarying.materialize(arg1);

            try {
                //TODO THIS QUERY COULD BE MORE THAN JUST SELECT *
                ResultSet resultSet =
                        statement.executeQuery("select *\n"
                                + "from \"" +
                                query_id +
                                "\".\"" +
                                window_uri +
                                "\"");

                DBTablePrinter.printResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });


        //RUNTIME DATA
        Tuple2<Integer, String> tuple = new Tuple2<>(1000, "Tito Boeri");
        windowAssigner.notify(tuple, 1000);
        windowAssigner.notify(tuple = new Tuple2<>(2001, "Renzo Piano"), tuple.v1);
        windowAssigner.notify(tuple = new Tuple2<>(3000, "Daniel Libeskind"), tuple.v1);
        windowAssigner.notify(tuple = new Tuple2<>(4000, "Norman Foster"), tuple.v1);
        windowAssigner.notify(tuple = new Tuple2<>(4003, "Tito Boeri2"), tuple.v1);
        windowAssigner.notify(tuple = new Tuple2<>(6001, "Norman Foster2"), tuple.v1);

    }

    private class Tester {

        Graph expected;

        public void test(Graph g) {
            g.stream().map(Triple.class::cast).forEach(triple ->
                    assertTrue(expected.contains(triple)));
        }

        public void setExpected(Graph expected) {
            this.expected = expected;
        }

    }
}
