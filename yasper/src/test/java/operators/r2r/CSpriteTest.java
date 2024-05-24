package operators.r2r;

import graph.jena.datatypes.JenaGraphOrBindings;
import graph.jena.operatorsimpl.r2r.rsp.BGP;
import graph.jena.operatorsimpl.r2r.rsp.TP;
import graph.jena.operatorsimpl.r2r.csprite.CSpriteR2R;
import graph.jena.operatorsimpl.r2r.csprite.HierarchySchema;
import graph.jena.operatorsimpl.r2r.csprite.R2RUpwardExtension;
import graph.jena.operatorsimpl.r2r.csprite.UpwardExtension;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.algebra.op.OpTriple;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingBuilder;
import org.apache.jena.sparql.engine.binding.BindingFactory;
import org.apache.jena.sparql.engine.binding.BindingRoot;
import org.apache.jena.sparql.graph.GraphFactory;
import org.junit.jupiter.api.Test;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.jena.graph.NodeFactory.createURI;
import static org.apache.jena.graph.NodeFactory.createVariable;
import static org.apache.jena.graph.Triple.create;
import static org.apache.jena.sparql.core.Var.alloc;
import static org.apache.jena.vocabulary.RDF.type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSpriteTest {

    @Test
    public void testCSpriteSchema() {
        HierarchySchema hierarchySchema = getHierarchySchema();

        Map<String, List<String>> schema = new HashMap<>();
        schema.put("http://test/Warm", Arrays.asList("http://test/Green", "http://test/Orange"));
        schema.put("http://test/Cool", Arrays.asList("http://test/Blue", "http://test/Violet"));
        assertEquals(schema, hierarchySchema.getSchema());
    }


    @Test
    public void testCSpriteSchemaLoading() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        UpwardExtension upwardExtension = new UpwardExtension(hierarchySchema.getSchema());
        assertEquals(Collections.singleton("http://test/Warm"), upwardExtension.getUpwardExtension("http://test/Orange"));
    }

    @Test
    public void testCSpriteR2RVariableTypes() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("color");
        Node p = createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        Node o = createVariable("type");
        Triple t = new Triple(s, p, o);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);
        cSpriteR2R.findTypes();
        assertEquals(Set.of("http://test/Warm", "http://test/Green", "http://test/Blue", "http://test/Violet", "http://test/Cool", "http://test/Orange"), cSpriteR2R.getQueriedTypes());
    }


    @Test
    public void testCSpriteR2RFixedTypes() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("color");
        Node p = type.asNode();
        Node o = createURI("http://test/Warm");
        Triple t = new Triple(s, p, o);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);
        assertEquals(Set.of("http://test/Warm"), cSpriteR2R.getQueriedTypes());
    }

    @Test
    public void testCSpritePruneTest() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("color");
        Node p = type.asNode();
        Node o = createURI("http://test/Warm");
        Triple t = new Triple(s, p, o);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Map<String, Set<String>> prunedHierarchy = cSpriteR2R.getHierachy();
        Map<String, Set<String>> expected = Map.of("http://test/Green", Set.of("http://test/Warm"), "http://test/Orange", Set.of("http://test/Warm"));
        assertEquals(expected, cSpriteR2R.getHierachy());
    }

    @Test
    public void testR2ROperator() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("warmColor");
        Node p = type.asNode();
        Node o = createURI("http://test/Warm");
        Triple t = new Triple(s, p, o);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Graph graph = GraphFactory.createGraphMem();
        Node a = type.asNode();
        graph.add(create(createURI("g1"), a, createURI("http://test/Green")));
        graph.add(create(createURI("b1"), a, createURI("http://test/Blue")));

        JenaGraphOrBindings eval = tp.eval(cSpriteR2R.eval(Collections.singletonList(new JenaGraphOrBindings(graph))));

        List<Binding> result = eval.getResult();

        Binding build = BindingBuilder.create().add(alloc("warmColor"), createURI("g1")).build();

        assertEquals(Collections.singletonList(build), result);

    }

    @Test
    public void testR2ROperatorAllVars() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("warmColor");
        Node p = type.asNode();
        Node o = alloc("type");
        Triple t = create(s, p, o);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);
        CSpriteR2R cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);

        Graph graph = GraphFactory.createGraphMem();
        Node a = type.asNode();
        Node green = createURI("http://test/Green");
        graph.add(create(createURI("g1"), a, green));
        Node blue = createURI("http://test/Blue");
        graph.add(create(createURI("b1"), a, blue));

        JenaGraphOrBindings eval = tp.eval(cSpriteR2R.eval(Collections.singletonList(new JenaGraphOrBindings(graph))));

        List<Binding> result = eval.getResult();

        Binding root = BindingRoot.create();
        Binding b1 = BindingFactory.binding(root, alloc("warmColor"), createURI("g1"), alloc("type"), green);
        Binding b2 = BindingFactory.binding(root, alloc("warmColor"), createURI("b1"), alloc("type"), blue);
        Binding b3 = BindingFactory.binding(root, alloc("warmColor"), createURI("g1"), alloc("type"), createURI("http://test/Warm"));
        Binding b4 = BindingFactory.binding(root, alloc("warmColor"), createURI("b1"), alloc("type"), createURI("http://test/Cool"));

        List<Binding> expected = List.of(b1, b2, b3, b4);
        assertTrue(expected.size() == result.size() && expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void testR2ROperatorBGP() {
        HierarchySchema hierarchySchema = getHierarchySchema();
        Node s = alloc("warmColor");
        Node p = type.asNode();
        Node o = createURI("http://test/Warm");
        Triple t1 = create(s, p, o);

        Node s2 = alloc("coolColor");
        Node o2 = createURI("http://test/Cool");
        Triple t2 = create(s2, p, o2);

        List<Triple> t11 = List.of(t1, t2);
        BasicPattern pattern = BasicPattern.wrap(t11);

        BGP bgp = new BGP(new OpBGP(pattern), null, null);

        CSpriteR2R cSpriteR2R = new CSpriteR2R(bgp, hierarchySchema);

        Graph graph = GraphFactory.createGraphMem();
        Node a = type.asNode();
        Node green = createURI("http://test/Green");
        graph.add(create(createURI("g1"), a, green));
        Node blue = createURI("http://test/Blue");
        graph.add(create(createURI("b1"), a, blue));

        JenaGraphOrBindings eval = bgp.eval(cSpriteR2R.eval(Collections.singletonList(new JenaGraphOrBindings(graph))));

        List<Binding> result = eval.getResult();

        Binding root = BindingRoot.create();
        Binding b1 = BindingFactory.binding(root, alloc("warmColor"), createURI("g1"), alloc("coolColor"), createURI("b1"));

        assertEquals(List.of(b1), result);

    }

    private HierarchySchema getHierarchySchema() {
        HierarchySchema hierarchySchema = new HierarchySchema();
        hierarchySchema.addSubClassOf("http://test/Green", "http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Orange", "http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Blue", "http://test/Cool");
        hierarchySchema.addSubClassOf("http://test/Violet", "http://test/Cool");
        return hierarchySchema;
    }

    @Test
    public void testSimpleHierarchy() {
        Map<String, List<String>> schema = new HashMap<>();
        schema.put("O2", Arrays.asList("O1", "O4"));
        schema.put("O3", Arrays.asList("O2", "O5"));
        schema.put("O5", Arrays.asList("O6"));
        RelationToRelationOperator<JenaGraphOrBindings> r2r = new R2RUpwardExtension(schema);

        Graph graph = GraphFactory.createGraphMem();
        Node a = type.asNode();
        graph.add(create(createURI("S1"), a, createURI("O2")));
        graph.add(create(createURI("S1"), createURI("P1"), createURI("O2")));

        Graph result = r2r.eval(List.of(new JenaGraphOrBindings(graph))).getContent();

        Graph expected = GraphFactory.createGraphMem();

        expected.add(create(createURI("S1"), a, createURI("O2")));
        expected.add(create(createURI("S1"), a, createURI("O3")));
        expected.add(create(createURI("S1"), createURI("P1"), createURI("O2")));

        assertTrue(expected.size() == result.size() && expected.stream().collect(Collectors.toList()).containsAll(result.stream().collect(Collectors.toList())) && result.stream().collect(Collectors.toList()).containsAll(expected.stream().collect(Collectors.toList())));


    }

    @Test
    public void testSimpleHierarchyQueryTest() {

        Map<String, List<String>> schema = new HashMap<>();
        schema.put("O2", Arrays.asList("O1", "O4"));
        schema.put("O3", Arrays.asList("O2", "O5"));
        schema.put("O5", Arrays.asList("O6"));
        RelationToRelationOperator<JenaGraphOrBindings> r2r = new R2RUpwardExtension(schema);

        Node a = type.asNode();

        Node s1 = Var.alloc("s");
        Node o1 = createURI("O3");
        Triple t = Triple.create(s1, a, o1);
        TP tp = new TP(new OpTriple(t).asBGP(), null, null);

        Graph graph = GraphFactory.createGraphMem();
        graph.add(create(createURI("S1"), a, createURI("O2")));
        graph.add(create(createURI("S1"), createURI("P1"), createURI("O2")));

        List<Binding> result = tp.eval(r2r.eval(List.of(new JenaGraphOrBindings(graph)))).getResult();

        Binding root = BindingRoot.create();
        Binding b1 = BindingFactory.binding(root, alloc("s"), createURI("S1"));

        assertEquals(List.of(b1), result);

    }
}
