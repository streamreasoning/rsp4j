package ee.ut.sr.rsp.binsper;

import org.apache.jena.graph.Graph;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpVisitor;
import org.apache.jena.sparql.algebra.op.*;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.graph.GraphFactory;

import java.util.ArrayList;
import java.util.List;

public class AlgebraTest {

    public static void main(String[] args) {

        Op parse = Algebra.parse("(triple ?s ?p ?o)");

        Op triple = new OpTriple(new Triple(NodeFactory.createVariable("s"), NodeFactory.createVariable("p"), NodeFactory.createVariable("o")));

        System.out.println(triple.equals(parse));

        Graph graphMem1 = GraphFactory.createGraphMem();
        Graph graphMem2 = GraphFactory.createGraphMem();

        graphMem1.add(new Triple(NodeFactory.createURI("s"), NodeFactory.createURI("p"), NodeFactory.createURI("o1")));
        graphMem2.add(new Triple(NodeFactory.createURI("s"), NodeFactory.createURI("p"), NodeFactory.createURI("o2")));

        Dataset dataset = DatasetFactory.create();

        dataset.addNamedModel(NodeFactory.createURI("file:///Users/riccardo/_Projects/yasper/g1").getURI(), ModelFactory.createModelForGraph(graphMem1));
        dataset.addNamedModel(NodeFactory.createURI("file:///Users/riccardo/_Projects/yasper/g2").getURI(), ModelFactory.createModelForGraph(graphMem2));

            System.out.println(
                    dataset.asDatasetGraph());
        List<Op> optriples = new ArrayList<>();
        List<Op> bgps = new ArrayList<>();

        Query query = QueryFactory.create(
                "CONSTRUCT {?s2 ?p ?o1} WHERE { " +
                        "GRAPH " + "<" + NodeFactory.createURI("file:///Users/riccardo/_Projects/yasper/g1").getURI() + ">" +
                        " { ?s1 ?p ?o1.} " +
                        "GRAPH " + "<" + NodeFactory.createURI("file:///Users/riccardo/_Projects/yasper/g2").getURI() + ">" +
                        "{ ?s2 ?p ?o2 .} " +
                        "}");

        System.out.println(query.toString());

        Op compile = Algebra.compile(query);

        System.out.println(compile);
        compile.visit(new OpVisitor() {
            @Override
            public void visit(OpBGP opBGP) {
                bgps.add(opBGP);
                opBGP.getPattern().forEach(triple1 -> optriples.add(new OpTriple(triple1)));
            }

            @Override
            public void visit(OpQuadPattern quadPattern) {

            }

            @Override
            public void visit(OpQuadBlock quadBlock) {

            }

            @Override
            public void visit(OpTriple opTriple) {
                optriples.add(opTriple);
            }

            @Override
            public void visit(OpQuad opQuad) {

            }

            @Override
            public void visit(OpPath opPath) {

            }

            @Override
            public void visit(OpTable opTable) {

            }

            @Override
            public void visit(OpNull opNull) {

            }

            @Override
            public void visit(OpProcedure opProc) {

            }

            @Override
            public void visit(OpPropFunc opPropFunc) {

            }

            @Override
            public void visit(OpFilter opFilter) {

            }

            @Override
            public void visit(OpGraph opGraph) {
                opGraph.getSubOp().visit(this);
            }

            @Override
            public void visit(OpService opService) {

            }

            @Override
            public void visit(OpDatasetNames dsNames) {

            }

            @Override
            public void visit(OpLabel opLabel) {

            }

            @Override
            public void visit(OpAssign opAssign) {

            }

            @Override
            public void visit(OpExtend opExtend) {

            }

            @Override
            public void visit(OpJoin opJoin) {
                opJoin.getLeft().visit(this);
                opJoin.getRight().visit(this);
            }

            @Override
            public void visit(OpLeftJoin opLeftJoin) {

            }

            @Override
            public void visit(OpUnion opUnion) {

            }

            @Override
            public void visit(OpDiff opDiff) {

            }

            @Override
            public void visit(OpMinus opMinus) {

            }

            @Override
            public void visit(OpConditional opCondition) {

            }

            @Override
            public void visit(OpSequence opSequence) {

            }

            @Override
            public void visit(OpDisjunction opDisjunction) {

            }

            @Override
            public void visit(OpList opList) {

            }

            @Override
            public void visit(OpOrder opOrder) {

            }

            @Override
            public void visit(OpProject opProject) {

            }

            @Override
            public void visit(OpReduced opReduced) {

            }

            @Override
            public void visit(OpDistinct opDistinct) {

            }

            @Override
            public void visit(OpSlice opSlice) {

            }

            @Override
            public void visit(OpGroup opGroup) {

            }

            @Override
            public void visit(OpTopN opTop) {

            }
        });


        optriples.forEach(System.out::println);
        bgps.forEach(System.out::println);
        QueryIterator exec = Algebra.exec(compile, dataset);

        exec.forEachRemaining(binding -> {
            System.out.println(binding);
        });



    }
}
