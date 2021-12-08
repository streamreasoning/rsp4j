package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class TriplePatternQueryTest {


    @Test
    public void testAllVariables() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT * " +
                                                                                     "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   ?s ?p ?o ." +
                                                                                     "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP TP = new TP(s, p, o);

        assertEquals(TP, query.r2r().getR2RComponents().get("default"));

    }

    @Test
    public void testAllTerms() throws InterruptedException {

        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT * " +
                                                                                     "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                                                                     "}");

        VarOrTerm s = new TermImpl(RDFUtils.createIRI("http://test/s"));
        VarOrTerm p = new TermImpl(RDFUtils.createIRI("http://test/p"));
        VarOrTerm o = new TermImpl(RDFUtils.createIRI("http://test/o"));
        TP TP = new TP(s, p, o);

        assertEquals(TP, query.r2r().getR2RComponents().get("default"));

    }

    @Test
    public void testSingleWindow() throws InterruptedException {

        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT * " +
                                                                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                                                                     "}");

        assertEquals(1, query.getWindowMap().size());

        for (Map.Entry<? extends WindowNode, DataStream<Graph>> entry : query.getWindowMap().entrySet()) {
            assertEquals(10 * 1000, entry.getKey().getRange());
            assertEquals(5 * 1000, entry.getKey().getStep());
            assertEquals("http://test/window", entry.getKey().iri());
            assertEquals(new DataStreamImpl<Graph>("http://test/stream"), entry.getValue());
        }

    }

    @Test
    public void testOutputStream() {
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT * " +
                                                                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                                                                     "}");

        assertEquals(new DataStreamImpl<>("http://out/stream"), query.getOutputStream());
    }

    @Test
    public void testOutputStreamType() {
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT * " +
                                                                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                                                                     "}");

        assertEquals(true, query.isIstream());
        assertEquals(false, query.isDstream());
        assertEquals(false, query.isRstream());
        query = TPQueryFactory.parse("" +
                                     "REGISTER DSTREAM <http://out/stream> AS " +
                                     "SELECT * " +
                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                     "WHERE {" +
                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                     "}");

        assertEquals(false, query.isIstream());
        assertEquals(true, query.isDstream());
        assertEquals(false, query.isRstream());
        query = TPQueryFactory.parse("" +
                                     "REGISTER RSTREAM <http://out/stream> AS " +
                                     "SELECT * " +
                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                     "WHERE {" +
                                     "   <http://test/s> <http://test/p> <http://test/o> ." +
                                     "}");

        assertEquals(false, query.isIstream());
        assertEquals(false, query.isDstream());
        assertEquals(true, query.isRstream());
    }

    @Test
    public void testAggregation() {
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                                                                                     "REGISTER ISTREAM <http://out/stream> AS " +
                                                                                     "SELECT (Count(?s) AS ?count)" +
                                                                                     "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                                                                                     "WHERE {" +
                                                                                     "   ?s <http://test/p> <http://test/o> ." +
                                                                                     "}");
        assertEquals(1, query.getAggregations().size());
        Aggregation expected = new Aggregation(null, "?s", "?count", "Count");
        assertEquals(expected, query.getAggregations().get(0));
    }
    @Test
    public void testProjection() {
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?s ?p " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   ?s ?p ?o ." +
                "}");
        List<Var> projections = new ArrayList<>();
        projections.add(new VarImpl("s"));
        projections.add(new VarImpl("p"));
        assertEquals(projections, query.getProjections());
    }

    @Test
    public void testFilter() {
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?s ?p " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   ?s ?p ?o . Filter(?s = ?o)" +
                "}");

        R2RPipe pipe = (R2RPipe) query.r2r().getR2RComponents().get("default");

        assertEquals(pipe.getR2rs().length, 2);
    }

}
