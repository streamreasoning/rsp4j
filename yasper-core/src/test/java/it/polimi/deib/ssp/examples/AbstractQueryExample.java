package it.polimi.deib.ssp.examples;

import it.polimi.deib.ssp.utils.StreamViewImpl;
import it.polimi.deib.ssp.utils.WritableStream;
import it.polimi.yasper.core.quering.TimeVarying;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.report.ReportImpl;
import it.polimi.yasper.core.spe.report.strategies.OnWindowClose;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.operator.CSPARQLTimeWindowOperator;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.utils.RDFUtils;
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
        WritableStream stream = new WritableStream();

        //WINDOW DECLARATION
        WindowOperator windowOperator = new CSPARQLTimeWindowOperator(RDFUtils.createIRI("w1"), 2000, 2000, scope);

        //ENGINE INTERNALS - HOW THE REPORTING POLICY, TICK AND REPORT GRAIN INFLUENCE THE RUNTIME
        WindowAssigner windowAssigner = windowOperator.apply(stream);
        windowAssigner.setReport(report);
        windowAssigner.setTick(tick);
        windowAssigner.setReportGrain(report_grain);

        StreamViewImpl v = new StreamViewImpl();
        TimeVarying timeVarying = windowAssigner.setView(v);
        v.addObserver((o, arg) -> {
            Long arg1 = (Long) arg;
            Graph g = (Graph) timeVarying.eval(arg1);
            System.err.println(arg1);
            System.err.println(g);
        });


        Graph graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S1"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O1")));

        //RUNTIME DATA
        stream.put(new WritableStream.Elem(1000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S2"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O2")));

        stream.put(new WritableStream.Elem(1000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S3"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O3")));

        stream.put(new WritableStream.Elem(2001, graph));
        graph = RDFUtils.getInstance().createGraph();

        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S4"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O4")));

        stream.put(new WritableStream.Elem(3000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S5"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O5")));

        stream.put(new WritableStream.Elem(5000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S6"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O6")));

        stream.put(new WritableStream.Elem(5000, graph));
        stream.put(new WritableStream.Elem(6000, graph));

        graph = RDFUtils.getInstance().createGraph();
        graph.add(RDFUtils.getInstance().createTriple(RDFUtils.getInstance().createIRI("S7"), RDFUtils.getInstance().createIRI("p"), RDFUtils.getInstance().createIRI("O7")));

        stream.put(new WritableStream.Elem(7000, graph));
        //stream.put(new WritableStream.Elem(3000, graph));


    }

}
