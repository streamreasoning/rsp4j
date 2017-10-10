package it.polimi.sr.onsper.tests.schema;

import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.sr.onsper.query.OBDAQuery;
import it.polimi.sr.onsper.query.OBSDAQueryBuilder;
import it.polimi.sr.onsper.query.schema.OntopJavaTypeFactory;
import it.polimi.sr.onsper.streams.RegisteredRelStream;
import it.polimi.yasper.core.enums.StreamOperator;
import it.unibz.inf.ontop.answering.reformulation.input.InputQuery;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.jdbc.CalciteJdbc41Factory;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.jdbc.Driver;
import org.apache.commons.rdf.api.Graph;
import org.junit.Before;
import org.semanticweb.owlapi.model.IRI;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SDSBuilderTest {


    private CalciteSchema rootSchema;
    private CalciteConnection calciteConnection;

    @Before
    public void init() throws ClassNotFoundException, SQLException {
        CalciteJdbc41Factory factory = new CalciteJdbc41Factory();
        rootSchema = CalciteSchema.createRootSchema(true);
        calciteConnection = factory.newConnection(new Driver(), factory, "jdbc:calcite:", new Properties(), rootSchema, new OntopJavaTypeFactory());
    }

    public void test() throws ClassNotFoundException {

        Map<String, Graph> mappings = new HashMap<>();

        Map<String, RegisteredRelStream> registeredStreams = new HashMap<>();

        OBSDAQueryBuilder builder = new OBSDAQueryBuilder(calciteConnection, registeredStreams, mappings);

        builder.visit(new MockupQuery());

    }


    private static class MockupQuery implements OBDAQuery {

        @Override
        public String getID() {
            return "test_query";
        }

        @Override
        public StreamOperator getR2S() {
            return null;
        }

        @Override
        public boolean isRecursive() {
            return false;
        }

        @Override
        public Set<? extends WindowOperator> getWindowsSet() {
            return null;
        }

        @Override
        public Set<? extends WindowOperator> getNamedWindowsSet() {
            return null;
        }

        @Override
        public Map<WindowOperator, Stream> getWindowMap() {
            return null;
        }

        @Override
        public Set<Stream> getStreamSet() {
            return null;
        }

        @Override
        public void accept(SDSBuilder v) {
            v.visit(this);
        }

        @Override
        public InputQuery getQ() {
            return null;
        }

        @Override
        public IRI getTBox() {
            return null;
        }
    }
}
