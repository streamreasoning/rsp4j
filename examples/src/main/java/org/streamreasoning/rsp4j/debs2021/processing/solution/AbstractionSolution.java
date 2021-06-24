package org.streamreasoning.rsp4j.debs2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.Task;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

public class AbstractionSolution {

    public static void main(String[] args) throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");


        //define output stream
        BindingStream outStream = new BindingStream("out");

        // Engine properties
        Report report = new ReportImpl();
        report.add(new OnWindowClose());
        //        report.add(new NonEmptyContent());
        //        report.add(new OnContentChange());
        //        report.add(new Periodic());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;
        Time instance = new TimeImpl(0);

        // WINDOW DECLARATION
        StreamToRelationOp<Graph, Graph> build = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        //R2R
        VarOrTerm s = new VarImpl("green");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Green");
        TP r2r = new TP(s, p, o);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        Task<Graph, Graph, Binding, Binding> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", build, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(inputStream)
                .addTask(t)
                .out(outStream)
                .build();

        outStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
        generator.startStreaming();
        Thread.sleep(20_000);
        generator.stopStreaming();
    }

}
