package org.streamreasoning.rsp4j.yasper;

import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BGP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;

public class BindingTest {

    @Test
    public void testSPO() {
        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("O1")));

        BGP bgp = new BGP(sds, s, p, o);

        List<Binding> collect = bgp.eval(0).collect(Collectors.toList());

        assertEquals(1, collect.size());

        Binding binding = collect.get(0);
        assertEquals(instance.createIRI("S1"), binding.value(new VarImpl("s")));
        assertEquals(instance.createIRI("p"), binding.value(new VarImpl("p")));
        assertEquals(instance.createIRI("O1"), binding.value(new VarImpl("o")));

    }

    @Test
    public void testPO() {
        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new TermImpl(instance.createIRI("S1"));
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("O1")));

        BGP bgp = new BGP(sds, s, p, o);


        List<Binding> collect = bgp.eval(0).collect(Collectors.toList());

        assertEquals(1, collect.size());

        Binding binding = collect.get(0);
        assertEquals(instance.createIRI("p"), binding.value(new VarImpl("p")));
        assertEquals(instance.createIRI("O1"), binding.value(new VarImpl("o")));

    }


    @Test
    public void testSPS() {
        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("s");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("p"), instance.createIRI("S1")));

        BGP bgp = new BGP(sds, s, p, o);

        List<Binding> collect = bgp.eval(0).collect(Collectors.toList());

        assertEquals(1, collect.size());

        Binding binding = collect.get(0);
        assertEquals(instance.createIRI("S1"), binding.value(new VarImpl("s")));
        assertEquals(instance.createIRI("p"), binding.value(new VarImpl("p")));

    }

    @Test
    public void testSSS() {
        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("S1"), instance.createIRI("S1")));

        BGP bgp = new BGP(sds, s, s, s);

        List<Binding> collect = bgp.eval(0).collect(Collectors.toList());

        assertEquals(1, collect.size());

        Binding binding = collect.get(0);
        assertEquals(instance.createIRI("S1"), binding.value(new VarImpl("s")));

    }

    @Test
    public void testSSL() {
        RDF instance = RDFUtils.getInstance();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm l = new VarImpl("lit");

        SDSImpl sds = new SDSImpl();

        sds.add(instance.createQuad(null, instance.createIRI("S1"), instance.createIRI("S1"), instance.createLiteral("string")));

        BGP bgp = new BGP(sds, s, s, l);

        List<Binding> collect = bgp.eval(0).collect(Collectors.toList());

        assertEquals(1, collect.size());

        Binding binding = collect.get(0);
        assertEquals(instance.createIRI("S1"), binding.value(new VarImpl("s")));
        assertEquals(instance.createLiteral("string"), binding.value(new VarImpl("lit")));

        System.out.println(binding);

    }
}
