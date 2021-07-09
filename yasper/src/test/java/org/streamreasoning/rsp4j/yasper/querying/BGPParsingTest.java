package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import static org.junit.Assert.assertEquals;

public class BGPParsingTest {

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
        assertEquals(bgp, query.r2r());

    }
}
