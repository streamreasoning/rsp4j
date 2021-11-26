package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MultipleWindowQueryTest {

    @Test
    public void testMultipleWindows() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <window1> ON <stream1> [RANGE PT10S STEP PT5S] " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "  WINDOW <window1> {?s ?p ?o .}" +
                "  WINDOW <window2> {?sz ?pz ?oz.}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("sz");
        VarOrTerm p2 = new VarImpl("pz");
        VarOrTerm o2 = new VarImpl("oz");
        TP tp2 = new TP(s2, p2, o2);


        Map<String, RelationToRelationOperator<Graph, Binding>> r2rs = new HashMap<>();
        RelationToRelationOperator<Graph, Binding> compbinedR2R = new MultipleGraphR2R(r2rs);
        r2rs.put("window1", tp);
        r2rs.put("window2",tp2);
        assertEquals(compbinedR2R, query.r2r());

    }
    @Test
    public void testDefaultGraphWithWindow() throws InterruptedException {


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM <http://test/default.rdf> " +
                "FROM NAMED WINDOW <window2> ON <stream2> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                " ?sz ?pz ?oz. " +
                "  WINDOW <window1> {?s ?p ?o .}" +
                "}");


        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("sz");
        VarOrTerm p2 = new VarImpl("pz");
        VarOrTerm o2 = new VarImpl("oz");
        TP tp2 = new TP(s2, p2, o2);


        Map<String, RelationToRelationOperator<Graph, Binding>> r2rs = new HashMap<>();
        RelationToRelationOperator<Graph, Binding> compbinedR2R = new MultipleGraphR2R(r2rs);
        r2rs.put("window1", tp);
        r2rs.put("default",tp2);
        assertEquals(compbinedR2R, query.r2r());

    }

}
