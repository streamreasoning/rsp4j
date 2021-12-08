package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import static org.junit.Assert.assertEquals;

public class BGPQueryTest {

    @Test
    public void testAllVariables() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "  WINDOW <window2> {?s ?p ?o ." +
                "                   ?s2 ?p2 ?o2.}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("s2");
        VarOrTerm p2 = new VarImpl("p2");
        VarOrTerm o2 = new VarImpl("o2");
        TP tp2 = new TP(s2, p2, o2);
        BGP bgp = BGP.createFrom(tp)
                .join(tp2)
                .create();
        assertEquals(bgp, query.r2r().getR2RComponents().get("window2"));

    }
    @Test
    public void testReocurringSubject() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "  WINDOW <window2> {?s ?p ?o ;" +
                "                       ?p2 ?o2.}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("s");
        VarOrTerm p2 = new VarImpl("p2");
        VarOrTerm o2 = new VarImpl("o2");
        TP tp2 = new TP(s2, p2, o2);
        BGP bgp = BGP.createFrom(tp)
                .join(tp2)
                .create();
        assertEquals(bgp, query.r2r().getR2RComponents().get("window2"));

    }
    @Test
    public void testReocurringObject() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "  WINDOW <window2> {?s ?p ?o,?o2 .}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("s");
        VarOrTerm p2 = new VarImpl("p");
        VarOrTerm o2 = new VarImpl("o2");
        TP tp2 = new TP(s2, p2, o2);
        BGP bgp = BGP.createFrom(tp)
                .join(tp2)
                .create();
        assertEquals(bgp, query.r2r().getR2RComponents().get("window2"));

    }
    @Test
    public void testReocurringSubjectAndObject() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "  WINDOW <window2> {?s ?p ?o ;" +
                "                       ?p2 ?o2, ?o3.}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("s");
        VarOrTerm p2 = new VarImpl("p2");
        VarOrTerm o2 = new VarImpl("o2");
        TP tp2 = new TP(s2, p2, o2);
        VarOrTerm o3 = new VarImpl("o3");
        TP tp3 = new TP(s2, p2, o3);
        BGP bgp = BGP.createFrom(tp)
                .join(tp2)
                .join(tp3)
                .create();
        assertEquals(bgp, query.r2r().getR2RComponents().get("window2"));

    }
}
