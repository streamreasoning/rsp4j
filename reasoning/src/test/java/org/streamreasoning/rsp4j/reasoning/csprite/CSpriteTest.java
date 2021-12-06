package org.streamreasoning.rsp4j.reasoning.csprite;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CSpriteTest {

    @Test
    public void testCSpriteSchema(){
        HierarchySchema hierarchySchema = getHierarchySchema();

        Map<String, List<String>> schema = new HashMap<>();
        schema.put("http://test/Warm", Arrays.asList("http://test/Green", "http://test/Orange"));
        schema.put("http://test/Cool", Arrays.asList( "http://test/Blue","http://test/Violet"));
        assertEquals(schema, hierarchySchema.getSchema());
    }



    @Test
    public void testCSpriteSchemaLoading(){
        HierarchySchema hierarchySchema = getHierarchySchema();
        UpwardExtension upwardExtension = new UpwardExtension(hierarchySchema.getSchema());
        assertEquals(Collections.singleton("http://test/Warm"),upwardExtension.getUpwardExtension("http://test/Orange"));
    }

    @Test
    public void testCSpriteR2RVariableTypes(){
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("color");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new VarImpl("type");
        TP tp = new TP(s, p, o);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);
        cSpriteR2R.findTypes();
        assertEquals(Set.of("http://test/Warm","http://test/Green","http://test/Blue","http://test/Violet","http://test/Cool","http://test/Orange"),cSpriteR2R.getQueriedTypes());
    }
    @Test
    public void testCSpriteR2RFixedTypes(){
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("color");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Warm");
        TP tp = new TP(s, p, o);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);
        assertEquals(Set.of("http://test/Warm"),cSpriteR2R.getQueriedTypes());
    }
    @Test
    public void testCSpritePruneTest(){
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("color");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Warm");
        TP tp = new TP(s, p, o);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Map<String, Set<String>> prunedHierarchy = cSpriteR2R.getHierachy();
        Map<String, Set<String>> expected = Map.of("http://test/Green", Set.of("http://test/Warm"), "http://test/Orange", Set.of("http://test/Warm"));
        assertEquals(expected,cSpriteR2R.getHierachy());
    }
    @Test
    public void testR2ROperator() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("warmColor");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Warm");
        TP tp = new TP(s, p, o);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Graph graph = RDFUtils.createGraph();
        IRI a = RDFUtils.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("g1"), a, RDFUtils.createIRI("http://test/Green")));
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("b1"), a, RDFUtils.createIRI("http://test/Blue")));

        Set<Binding> result = cSpriteR2R.eval(Stream.of(graph)).collect(Collectors.toSet());
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("warmColor"), RDFUtils.createIRI("g1"));

        assertEquals(Set.of(b1), result);

    }
    @Test
    public void testR2ROperatorAllVars() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("warmColor");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new VarImpl("?type");
        TP tp = new TP(s, p, o);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Graph graph = RDFUtils.createGraph();
        IRI a = RDFUtils.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("g1"), a, RDFUtils.createIRI("http://test/Green")));
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("b1"), a, RDFUtils.createIRI("http://test/Blue")));

        Set<Binding> result = cSpriteR2R.eval(Stream.of(graph)).collect(Collectors.toSet());
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("warmColor"), RDFUtils.createIRI("g1"));
        b1.add(new VarImpl("type"), RDFUtils.createIRI("http://test/Green"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("warmColor"), RDFUtils.createIRI("b1"));
        b2.add(new VarImpl("type"), RDFUtils.createIRI("http://test/Blue"));
        Binding b3 = new BindingImpl();
        b3.add(new VarImpl("warmColor"), RDFUtils.createIRI("g1"));
        b3.add(new VarImpl("type"), RDFUtils.createIRI("http://test/Warm"));
        Binding b4 = new BindingImpl();
        b4.add(new VarImpl("warmColor"), RDFUtils.createIRI("b1"));
        b4.add(new VarImpl("type"), RDFUtils.createIRI("http://test/Cool"));
        assertEquals(Set.of(b1,b2,b3,b4), result);

    }
    @Test
    public void testR2ROperatorBGP() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        VarOrTerm s = new VarImpl("warmColor");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Warm");
        TP tp = new TP(s, p, o);
        VarOrTerm s2 = new VarImpl("coolColor");
        VarOrTerm o2 = new TermImpl("http://test/Cool");
        TP tp2 = new TP(s2, p, o2);
        BGP bgp = BGP.createFrom(tp).join(tp2).create();
        CSpriteR2R cSpriteR2R = new CSpriteR2R(bgp, hierarchySchema);

        Graph graph = RDFUtils.createGraph();
        IRI a = RDFUtils.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("g1"), a, RDFUtils.createIRI("http://test/Green")));
        graph.add(RDFUtils.createTriple(RDFUtils.createIRI("b1"), a, RDFUtils.createIRI("http://test/Blue")));

        Set<Binding> result = cSpriteR2R.eval(Stream.of(graph)).collect(Collectors.toSet());
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("warmColor"), RDFUtils.createIRI("g1"));
        b1.add(new VarImpl("coolColor"), RDFUtils.createIRI("b1"));

        assertEquals(Set.of(b1), result);

    }
    private HierarchySchema getHierarchySchema() {
        HierarchySchema hierarchySchema = new HierarchySchema();
        hierarchySchema.addSubClassOf("http://test/Green","http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Orange","http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Blue","http://test/Cool");
        hierarchySchema.addSubClassOf("http://test/Violet","http://test/Cool");
        return hierarchySchema;
    }
}
