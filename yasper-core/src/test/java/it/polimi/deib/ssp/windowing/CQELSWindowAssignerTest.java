package it.polimi.deib.ssp.windowing;

import it.polimi.deib.ssp.utils.StreamViewImpl;
import it.polimi.deib.ssp.utils.WritableStream;
import it.polimi.yasper.core.quering.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.report.ReportImpl;
import it.polimi.yasper.core.spe.report.strategies.OnContentChange;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.time.TimeImpl;
import it.polimi.yasper.core.spe.windowing.assigner.CQELSWindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.utils.RDFUtils;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class CQELSWindowAssignerTest {

    @Test
    public void test() {

        Report report = new ReportImpl();

        report.add(new OnContentChange());

        Tick tick = Tick.TUPLE_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        WindowAssigner<Graph> wa = new CQELSWindowAssigner(RDFUtils.createIRI("w1"), 3000, 0, new TimeImpl());

        wa.report(report);
        wa.tick(tick);
        wa.report_grain(report_grain);

        Tester tester = new Tester();

        StreamViewImpl v = new StreamViewImpl();
        TimeVarying timeVarying = wa.set(v);


        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.out.println(arg1);
            System.out.println(timeVarying.get());
            tester.test((Graph) timeVarying.get());
        });


        Graph expected = RDFUtils.getInstance().createGraph();
        Graph unexpected = RDFUtils.getInstance().createGraph();

        Graph graph = RDFUtils.getInstance().createGraph();
        Triple triple = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1"));
        graph.add(triple);
        expected.add(triple);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        //RUNTIME DATA
        wa.notify(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple1 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2"));
        graph.add(triple1);
        expected.add(triple1);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 2001);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple2 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3"));
        graph.add(triple2);

        expected.add(triple2);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 3000);
        graph = RDFUtils.getInstance().createGraph();

        Triple triple3 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4"));
        graph.add(triple3);

        expected.add(triple3);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 4000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple4 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5"));

        graph.add(triple4);

        expected.add(triple4);
        expected.remove(triple);
        tester.setExpected_rstream(expected);

        unexpected.add(triple);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 5000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple5 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6"));
        graph.add(triple5);

        expected.add(triple5);
        expected.remove(triple1);
        tester.setExpected_rstream(expected);

        unexpected.add(triple1);
        tester.setUnexpected_dstream(unexpected);


        wa.notify(graph, 6000);
        graph = RDFUtils.getInstance().createGraph();
        Triple triple6 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S62"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O62"));
        graph.add(triple6);

        expected.add(triple6);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 6000);
        wa.notify(graph, 6000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple7 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7"));

        graph.add(triple7);

        expected.add(triple7);
        expected.remove(triple2);
        tester.setExpected_rstream(expected);

        unexpected.add(triple2);
        tester.setUnexpected_dstream(unexpected);

        wa.notify(graph, 7000);

    }

    private class Tester {

        Graph expected_rstream;

        Graph unexpected_dstream;

        public void test(Graph g) {
            expected_rstream.stream().map(Triple.class::cast).forEach(triple ->
                    assertTrue(g.contains(triple)));

            unexpected_dstream.stream().map(Triple.class::cast).forEach(triple ->
                    assertTrue(!g.contains(triple)));
        }

        public void setUnexpected_dstream(Graph unexpected_dstream) {
            this.unexpected_dstream = unexpected_dstream;
        }


        public void setExpected_rstream(Graph expected_rstream) {
            this.expected_rstream = expected_rstream;
        }

    }
}
