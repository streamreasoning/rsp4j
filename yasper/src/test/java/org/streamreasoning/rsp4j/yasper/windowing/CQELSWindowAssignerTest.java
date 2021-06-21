package org.streamreasoning.rsp4j.yasper.windowing;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2s.WindowParameter;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnContentChange;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.yasper.StreamViewImpl;
import org.streamreasoning.rsp4j.yasper.content.BindingContentFactory;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CQELSStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CQELSTimeWindowOperatorBinding;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CQELSTimeWindowOperatorBindingFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.lang.reflect.InvocationTargetException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.streamreasoning.rsp4j.api.operators.r2s.WindowParameter.wrap;

public class CQELSWindowAssignerTest {

    @Test
    public void genericConstruct() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Report report = new ReportImpl();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");


        ContentFactory<Graph, Binding> gcf = new BindingContentFactory(s, p, o);

        report.add(new OnContentChange());

        Tick tick = Tick.TUPLE_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        TimeImpl time = new TimeImpl(0);

        ContentFactory<Graph, Binding> cf = new BindingContentFactory(s, p, o);

        CQELSTimeWindowOperatorBindingFactory factory = new CQELSTimeWindowOperatorBindingFactory(time, tick, report, report_grain, cf);

        WindowParameter rangep = wrap(10000L);

        assertEquals(10000L, rangep.get());
        assertEquals(Long.class, rangep.type());


        IRI iri = RDFUtils.createIRI("iri");
        WindowParameter irip = wrap(iri);

        assertEquals(iri, irip.get());

        CQELSTimeWindowOperatorBinding<Graph, Binding> op = new CQELSTimeWindowOperatorBinding<>(iri, 10000L, time, tick, report, report_grain, gcf);

        StreamToRelationOp<Graph, Binding> build = factory.build(
                iri,
                10000L,
                time,
                tick,
                report,
                report_grain,
                gcf
        );

        assertEquals(tick, build.tick());
        assertEquals(time, build.time());
        assertEquals(report, build.report());
        assertEquals(report_grain, build.grain());

    }


    @Test
    public void testBGP() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Report report = new ReportImpl();

        VarOrTerm s = new VarImpl("s");
        VarOrTerm p = new VarImpl("p");
        VarOrTerm o = new VarImpl("o");


        ContentFactory<Graph, Binding> gcf = new BindingContentFactory(s, p, o);

        report.add(new OnContentChange());

        Tick tick = Tick.TUPLE_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        TimeImpl time = new TimeImpl(0);

        ContentFactory<Graph, Binding> cf = new BindingContentFactory(s, p, o);

        CQELSTimeWindowOperatorBindingFactory factory = new CQELSTimeWindowOperatorBindingFactory(time, tick, report, report_grain, cf);

        CQELSTimeWindowOperatorBinding<Graph, Binding> op = (CQELSTimeWindowOperatorBinding<Graph, Binding>) factory.build(
                RDFUtils.createIRI("iri"),
                3000L,
                time,
                tick,
                report,
                report_grain,
                gcf
        );

        RDF rdf = RDFUtils.getInstance();

        Graph graph = rdf.createGraph();

        graph.add(rdf.createIRI("S1"), rdf.createIRI("p"), rdf.createIRI("O1"));
        graph.add(rdf.createIRI("S2"), rdf.createIRI("q"), rdf.createIRI("O2"));
        graph.add(rdf.createIRI("S3"), rdf.createIRI("p"), rdf.createIRI("O3"));


        op.windowing(graph, 1000);
        Content<Graph, Binding> content = op.content(1000);
        System.out.println(content.coalesce());

        graph = rdf.createGraph();

        graph.add(rdf.createIRI("S4"), rdf.createIRI("p"), rdf.createIRI("O4"));
        graph.add(rdf.createIRI("S5"), rdf.createIRI("q"), rdf.createIRI("O5"));
        graph.add(rdf.createIRI("S6"), rdf.createIRI("p"), rdf.createIRI("O6"));
        op.windowing(graph, 1001);

        content = op.content(2001);

        System.out.println(content.coalesce());


    }

    @Test
    public void test() {

        Report report = new ReportImpl();

        report.add(new OnContentChange());

        SDS<Graph> sds = new SDSImpl();

        Tick tick = Tick.TUPLE_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        TimeImpl time = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> wa = new CQELSStreamToRelationOp(RDFUtils.createIRI("w1"), 3000, time, tick, report, report_grain, new GraphContentFactory());

        Tester tester = new Tester();

        StreamViewImpl v = new StreamViewImpl();
        TimeVarying timeVarying = wa.get();

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
        int current_time = 1000;
        wa.notify(graph, current_time);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple1 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2"));
        graph.add(triple1);
        expected.add(triple1);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        current_time = 2001;
        wa.notify(graph, current_time);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple2 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3"));
        graph.add(triple2);

        expected.add(triple2);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        current_time = 3000;

        wa.notify(graph, current_time);
        graph = RDFUtils.getInstance().createGraph();

        Triple triple3 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4"));
        graph.add(triple3);

        expected.add(triple3);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        current_time = 4000;

        wa.notify(graph, current_time);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple4 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5"));

        graph.add(triple4);

        expected.add(triple4);
        expected.remove(triple);
        tester.setExpected_rstream(expected);

        unexpected.add(triple);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        current_time = 5000;

        wa.notify(graph, current_time);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple5 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6"));
        graph.add(triple5);

        expected.add(triple5);
        expected.remove(triple1);
        tester.setExpected_rstream(expected);

        unexpected.add(triple1);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        current_time = 6000;

        wa.notify(graph, current_time);
        graph = RDFUtils.getInstance().createGraph();
        Triple triple6 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S62"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O62"));
        graph.add(triple6);

        expected.add(triple6);
        tester.setExpected_rstream(expected);
        tester.setUnexpected_dstream(unexpected);

        assertEquals(current_time, time.getAppTime());

        wa.notify(graph, 6000);

        assertEquals(current_time, time.getAppTime());

        wa.notify(graph, 6000);

        graph = RDFUtils.getInstance().createGraph();
        Triple triple7 = RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7"));

        graph.add(triple7);

        expected.add(triple7);
        expected.remove(triple2);
        tester.setExpected_rstream(expected);

        unexpected.add(triple2);
        tester.setUnexpected_dstream(unexpected);

        current_time = 7000;
        wa.notify(graph, current_time);

        assertEquals(current_time, time.getAppTime());


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
