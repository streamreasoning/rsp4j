package it.polimi.deib.rsp.test.examples;

import it.polimi.deib.sr.rsp.yasper.StreamViewImpl;
import it.polimi.deib.sr.rsp.yasper.examples.RDFStream;
import it.polimi.deib.sr.rsp.yasper.sds.SDSImpl;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;
import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.operators.s2r.StreamToRelationOperatorFactory;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.secret.report.ReportImpl;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.OnWindowClose;
import it.polimi.deib.sr.rsp.api.secret.time.TimeFactory;
import org.apache.commons.rdf.api.Graph;

public class AbstractQueryExample {


    public static void main(String[] str) {

        //ENGINE DEFINITION
        Report report = new ReportImpl();
        report.add(new OnWindowClose());
//        report.add(new NonEmptyContent());
//        report.add(new OnContentChange());
//        report.add(new Periodic());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        int scope = 0;

        //QUERY

        SDS sds = new SDSImpl();
        //STREAM DECLARATION
        RDFStream stream = new RDFStream("s1");

        //WINDOW DECLARATION
        StreamToRelationOperatorFactory<Graph, Graph> windowOperator = new CSPARQLTimeWindowOperatorFactory(2000, 2000, scope, TimeFactory.getInstance(), tick, report, report_grain, null);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME


        TimeVarying<Graph> timeVarying = windowOperator.apply(stream,RDFUtils.createIRI("w1"));

        StreamViewImpl v = new StreamViewImpl();

        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            timeVarying.materialize(arg1);
            System.err.println(arg1);
            System.err.println(timeVarying.get());
        });


        Graph graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1")));

        //RUNTIME DATA
        stream.put(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2")));

        stream.put(graph, 1000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3")));

        stream.put(graph, 2001);
        graph = RDFUtils.getInstance().createGraph();

        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4")));

        stream.put(graph, 3000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5")));

        stream.put(graph, 5000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6")));

        stream.put(graph, 5000);
        stream.put(graph, 6000);

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7")));

        stream.put(graph, 7000);
        //stream.put(new WritableStream.Elem(3000, graph));


    }

}
