package org.streamreasoning.rsp4j.reasoning.datalog;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class DatalogR2RTest {

    @Test
    public void testDatalogR2R(){
        DatalogR2R datalogR2R = new DatalogR2R();
        ReasonerTriple head = new ReasonerTriple("?x", "a", "Person");
        ReasonerTriple body1 = new ReasonerTriple("?x", "a", "Student");
        Rule r = new Rule(head,body1);

        datalogR2R.addRule(r);
        Triple commonsTriple = RDFUtils.createTriple(RDFUtils.createIRI("s"), RDFUtils.createIRI("a"), RDFUtils.createIRI("Student"));
        Graph g = RDFUtils.createGraph();
        g.add(commonsTriple);
        datalogR2R.addFacts(g);
        Graph materialization = datalogR2R.eval(Stream.of(g)).findFirst().get();
        assertEquals(2,materialization.size());
    }
}
