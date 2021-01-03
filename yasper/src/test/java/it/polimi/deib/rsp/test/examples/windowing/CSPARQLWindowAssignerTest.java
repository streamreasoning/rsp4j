package it.polimi.deib.rsp.test.examples.windowing;

import it.polimi.deib.sr.rsp.yasper.sds.SDSImpl;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import it.polimi.deib.sr.rsp.yasper.StreamViewImpl;
import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.secret.report.ReportImpl;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.OnWindowClose;
import it.polimi.deib.sr.rsp.api.secret.time.TimeImpl;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class CSPARQLWindowAssignerTest {

    @Test
    public void test() {

        Report report = new ReportImpl();
        report.add(new OnWindowClose());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        SDS<Graph> sds = new SDSImpl();

        //WINDOW DECLARATION
        TimeImpl time = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> windowStreamToRelationOp = new CSPARQLStreamToRelationOp(RDFUtils.createIRI("w1"), 2000, 2000, time, tick, report, report_grain);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying<Graph> timeVarying = windowStreamToRelationOp.get();

        Tester tester = new Tester();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
            tester.test((Graph) timeVarying.get());
        });

        Graph expected = RDFUtils.getInstance().createGraph();

        Graph graph = RDFUtils.getInstance().createGraph();
        Triple triple = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1"));
        graph.add(triple);
        expected.add(triple);

        tester.setExpected(graph);
        //RUNTIME DATA
        windowStreamToRelationOp.notify(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple1 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2"));
        graph.add(triple1);

        expected.add(triple1);
        tester.setExpected(expected);
        windowStreamToRelationOp.notify(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple2 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3"));

        graph.add(triple2);
        expected.add(triple2);
        tester.setExpected(expected);

        windowStreamToRelationOp.notify(graph, 2001);
        graph = RDFUtils.getInstance().createGraph();

        Triple triple3 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4"));
        graph.add(triple3);

        expected.add(triple3);
        tester.setExpected(expected);

        windowStreamToRelationOp.notify(graph, 3000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple4 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5"));
        graph.add(triple4);

        expected.add(triple4);
        tester.setExpected(expected);

        windowStreamToRelationOp.notify(graph, 5000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple5 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6"));
        graph.add(triple5);

        expected.add(triple5);
        tester.setExpected(expected);

        windowStreamToRelationOp.notify(graph, 5000);
        windowStreamToRelationOp.notify(graph, 5000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple6 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7"));
        graph.add(triple6);

        expected.add(triple6);
        tester.setExpected(expected);

        windowStreamToRelationOp.notify(graph, 7000);
    }

    private class Tester {

        Graph expected;

        public void test(Graph g) {
            g.stream().map(Triple.class::cast).forEach(triple ->
                    assertTrue(expected.contains(triple)));
        }

        public void setExpected(Graph expected) {
            this.expected = expected;
        }

    }
}
