package org.streamreasoning.rsp4j.yasper.querying;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.sds.DataSetImpl;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StaticR2RTest {
  @Test
  public void testStatic() {
    VarOrTerm s = new VarImpl("green");
    VarOrTerm s2 = new VarImpl("red");
    VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    VarOrTerm p2 = new TermImpl("http://color#source");
    VarOrTerm o = new TermImpl("http://color#Green");

    VarOrTerm o2 = new TermImpl("http://color#Red");
    VarOrTerm o3 = new VarImpl("source");
    TP tp = new TP(s, p, o);
    TP tp2 = new TP(s2, p, o2);
    TP tp3 = new TP(s, p2, o3);
    TP tp4 = new TP(s2, p2, o3);
    BGP bgp = BGP.createFrom(tp).join(tp2).join(tp3).join(tp4).create();
    URL fileURL = StaticR2RTest.class.getClassLoader().getResource(
            "colors.nt");

    DataSet<Graph> staticDataSet = new DataSetImpl("default", fileURL.getPath(), RDFBase.NT);
    // create a graph
    Stream<Graph> g = staticDataSet.getContent().stream();

    Stream<Binding> bindings = bgp.eval(g);

    Binding expected = new BindingImpl();
    expected.add(new VarImpl("red"), RDFUtils.createIRI("S7"));

    expected.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
    expected.add(new VarImpl("source"), RDFUtils.createIRI("Source1"));

    assertEquals(createSet(expected), bindings.collect(Collectors.toSet()));
    }


    private Set<Binding> createSet(Binding... bindings){
        return new HashSet<>(Arrays.asList(bindings));
    }
}
