package org.streamreasoning.rsp4j.abstraction.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BGPTest {

    @Test
    public void testSingleJoin(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o1 = new VarImpl("type");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("http://test/hasName");
        VarOrTerm o2 = new VarImpl("name");
        TP tp2 = new TP(s1,p2,o2);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,s1)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createGraph());

        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("type"), RDFUtils.createIRI("http://color#Green"));
        expected.add(new VarImpl("name"), RDFUtils.createLiteral("green"));
        assertEquals(expected,bindings.findAny().get());

    }
    @Test
    public void testDuplicateJoin(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o1 = new VarImpl("type");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("http://test/hasName");
        VarOrTerm o2 = new VarImpl("name");
        TP tp2 = new TP(s1,p2,o2);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,s1)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createDuplicateGraph());

        Stream<Binding> bindings = bgp.eval(g);

        Binding expected1 = new BindingImpl();
        expected1.add(new VarImpl("color"), RDFUtils.createIRI("S1"));
        expected1.add(new VarImpl("type"), RDFUtils.createIRI("http://color#Green"));
        expected1.add(new VarImpl("name"), RDFUtils.createLiteral("green"));

        Binding expected2 = new BindingImpl();
        expected2.add(new VarImpl("color"), RDFUtils.createIRI("S1"));
        expected2.add(new VarImpl("type"), RDFUtils.createIRI("http://color#Green"));
        expected2.add(new VarImpl("name"), RDFUtils.createLiteral("specialgreen"));

        Binding expected3 = new BindingImpl();
        expected3.add(new VarImpl("color"), RDFUtils.createIRI("S1"));
        expected3.add(new VarImpl("type"), RDFUtils.createIRI("http://color#SpecialGreen"));
        expected3.add(new VarImpl("name"), RDFUtils.createLiteral("green"));

        Binding expected4 = new BindingImpl();
        expected4.add(new VarImpl("color"), RDFUtils.createIRI("S1"));
        expected4.add(new VarImpl("type"), RDFUtils.createIRI("http://color#SpecialGreen"));
        expected4.add(new VarImpl("name"), RDFUtils.createLiteral("specialgreen"));
        Set<Binding> expectedBindings = new HashSet<>();
        expectedBindings.add(expected1);
        expectedBindings.add(expected2);
        expectedBindings.add(expected3);
        expectedBindings.add(expected4);
        assertEquals(expectedBindings,bindings.collect(Collectors.toSet()));

    }
    @Test
    public void testNoTPMathJoin(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o1 = new VarImpl("type");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("http://test/NonExistingRelation");
        VarOrTerm o2 = new VarImpl("name");
        TP tp2 = new TP(s1,p2,o2);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,s1)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createGraph());
        Stream<Binding> bindings = bgp.eval(g);


        assertFalse(bindings.findAny().isPresent());

    }
    @Test
    public void testStar(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o1 = new VarImpl("type");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("http://test/hasName");
        VarOrTerm o2 = new VarImpl("name");
        TP tp2 = new TP(s1,p2,o2);
        VarOrTerm p3 = new TermImpl("http://test/hasFrequency");
        VarOrTerm o3 = new VarImpl("freq");
        TP tp3 = new TP(s1,p3,o3);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,s1)
                .joinOn(tp3,s1)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createLargeGraph());

        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("type"), RDFUtils.createIRI("http://color#Green"));
        expected.add(new VarImpl("name"), RDFUtils.createLiteral("green"));
        expected.add(new VarImpl("freq"), RDFUtils.createLiteral("highFrequency"));
        assertEquals(expected,bindings.findAny().get());

    }
    @Test
    public void testSequence(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("location");
        VarOrTerm o1 = new VarImpl("loc");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("country");
        VarOrTerm o2 = new VarImpl("country");
        TP tp2 = new TP(o1,p2,o2);
        VarOrTerm p3 = new TermImpl("abbrivation");
        VarOrTerm o3 = new VarImpl("ab");
        TP tp3 = new TP(o2,p3,o3);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,o1)
                .joinOn(tp3,o2)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createLargeGraph());
        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("loc"), RDFUtils.createIRI("L1"));
        expected.add(new VarImpl("country"), RDFUtils.createIRI("Belgium"));
        expected.add(new VarImpl("ab"), RDFUtils.createLiteral("BE"));
        assertEquals(expected,bindings.findAny().get());
    }
    @Test
    public void testCycle(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("location");
        VarOrTerm o1 = new VarImpl("loc");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("country");
        VarOrTerm o2 = new VarImpl("country");
        TP tp2 = new TP(o1,p2,o2);
        VarOrTerm p3 = new TermImpl("observes");
        TP tp3 = new TP(o2,p3,s1);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,o1)
                .joinOn(tp3,o2)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createLargeGraph());
        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("loc"), RDFUtils.createIRI("L1"));
        expected.add(new VarImpl("country"), RDFUtils.createIRI("Belgium"));
        assertEquals(expected,bindings.findAny().get());
    }
    @Test
    public void testTree(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("location");
        VarOrTerm o1 = new VarImpl("loc");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm p2 = new TermImpl("country");
        VarOrTerm o2 = new VarImpl("country");
        TP tp2 = new TP(o1,p2,o2);
        VarOrTerm p3 = new TermImpl("abbrivation");
        VarOrTerm o3 = new VarImpl("ab");
        TP tp3 = new TP(o2,p3,o3);
        VarOrTerm p4 = new TermImpl("http://test/hasName");
        VarOrTerm o4 = new VarImpl("name");
        TP tp4 = new TP(s1,p4,o4);
        VarOrTerm p5 = new TermImpl("name");
        VarOrTerm o5 = new VarImpl("locationName");
        TP tp5 = new TP(o1,p5,o5);
        VarOrTerm p6 = new TermImpl("containedIn");
        VarOrTerm o6 = new VarImpl("continent");
        TP tp6 = new TP(o2,p6,o6);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,o1)
                .joinOn(tp3,o2)
                .joinOn(tp4,s1)
                .joinOn(tp5,o1)
                .joinOn(tp6,o2)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createLargeGraph());
        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("loc"), RDFUtils.createIRI("L1"));
        expected.add(new VarImpl("country"), RDFUtils.createIRI("Belgium"));
        expected.add(new VarImpl("ab"), RDFUtils.createLiteral("BE"));
        expected.add(new VarImpl("name"), RDFUtils.createLiteral("green"));
        expected.add(new VarImpl("locationName"), RDFUtils.createLiteral("locationName"));
        expected.add(new VarImpl("continent"), RDFUtils.createIRI("Europe"));

        assertEquals(expected,bindings.findAny().get());
    }
    @Test
    public void testNoJoin(){
        VarOrTerm s1 = new VarImpl("color");
        VarOrTerm p1 = new TermImpl("location");
        VarOrTerm o1 = new VarImpl("loc");
        TP tp1 = new TP(s1,p1,o1);
        VarOrTerm s2 = new VarImpl("country");
        VarOrTerm p2 = new TermImpl("containedIn");
        VarOrTerm o2 = new VarImpl("continent");
        TP tp2 = new TP(s2,p2,o2);
        BGP bgp = BGP.createFrom(tp1)
                .joinOn(tp2,o1)
                .create();

        //create a graph
        Stream<Graph> g = Stream.of(createLargeGraph());
        Stream<Binding> bindings = bgp.eval(g);

        Binding expected = new BindingImpl();
        expected.add(new VarImpl("color"), RDFUtils.createIRI("S1"));

        expected.add(new VarImpl("loc"), RDFUtils.createIRI("L1"));
        expected.add(new VarImpl("country"), RDFUtils.createIRI("Belgium"));
        expected.add(new VarImpl("continent"), RDFUtils.createIRI("Europe"));
        assertEquals(expected,bindings.findAny().get());
    }
    private Graph createGraph(){
        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("http://test/hasName"), instance.createLiteral("green")));
        return graph;
    }
    private Graph createDuplicateGraph(){
        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#SpecialGreen")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("http://test/hasName"), instance.createLiteral("green")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("http://test/hasName"), instance.createLiteral("specialgreen")));
        return graph;
    }
    private Graph createLargeGraph(){
        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("http://test/hasName"), instance.createLiteral("green")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("http://test/hasFrequency"), instance.createLiteral("highFrequency")));
        graph.add(instance.createTriple(instance.createIRI("S1"), instance.createIRI("location"), instance.createIRI("L1")));
        graph.add(instance.createTriple(instance.createIRI("L1"), p, instance.createIRI("Location")));
        graph.add(instance.createTriple(instance.createIRI("L1"), instance.createIRI("country"), instance.createIRI("Belgium")));
        graph.add(instance.createTriple(instance.createIRI("L1"), instance.createIRI("name"), instance.createLiteral("locationName")));
        graph.add(instance.createTriple(instance.createIRI("Belgium"), instance.createIRI("abbrivation"), instance.createLiteral("BE")));
        graph.add(instance.createTriple(instance.createIRI("Belgium"), instance.createIRI("observes"), instance.createIRI("S1")));
        graph.add(instance.createTriple(instance.createIRI("Belgium"), instance.createIRI("observes"), instance.createIRI("S2")));

        graph.add(instance.createTriple(instance.createIRI("Belgium"), instance.createIRI("containedIn"), instance.createIRI("Europe")));
        return graph;
    }
}
