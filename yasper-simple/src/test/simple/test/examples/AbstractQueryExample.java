package simple.test.examples;

import simple.windowing.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.spe.Tick;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.report.ReportImpl;
import it.polimi.yasper.core.spe.report.strategies.OnWindowClose;
import it.polimi.yasper.core.spe.time.TimeFactory;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.stream.rdf.RegisteredRDFStream;
import it.polimi.yasper.core.rspql.RDFUtils;
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

        //STREAM DECLARATION
        RegisteredRDFStream<Graph> stream = new RegisteredRDFStream<>("s1");

        //WINDOW DECLARATION
        WindowOperator windowOperator = new CSPARQLTimeWindowOperator(RDFUtils.createIRI("w1"), 2000, 2000, scope, TimeFactory.getInstance());

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME
        WindowAssigner windowAssigner = windowOperator.apply(stream);
        windowAssigner.report(report);
        windowAssigner.tick(tick);
        windowAssigner.report_grain(report_grain);

        StreamViewImpl v = new StreamViewImpl();

        TimeVarying timeVarying = windowAssigner.set(v);

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
