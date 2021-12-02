package org.streamreasoning.rsp4j.bigdata2021.processing.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.TaskAbstractionImpl;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
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
import org.streamreasoning.rsp4j.bigdata2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.examples.operators.r2r.SimpleR2RFilter;
import org.streamreasoning.rsp4j.reasoning.datalog.DatalogR2R;
import org.streamreasoning.rsp4j.reasoning.datalog.ReasonerTriple;
import org.streamreasoning.rsp4j.reasoning.datalog.Rule;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

import java.util.List;

public class CustomR2RExample {

  public static void main(String[] args) throws InterruptedException {
    // Setup the stream generator
    StreamGenerator generator = new StreamGenerator();
    DataStream<Graph> observationStream = generator.getObservationStream();
    DataStream<Graph> tracingStream = generator.getContactStream();
    DataStream<Graph> covidStream = generator.getCovidStream();

    // define output stream
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

    // Window (S2R) declaration incl. window name, window range (1s), window step (1s), start time
    // (instance) etc.
    StreamToRelationOp<Graph, Graph> w1 =
        new CSPARQLStreamToRelationOp<>(
            RDFUtils.createIRI("w1"),
            600_000,
            60_000,
            instance,
            tick,
            report,
            report_grain,
            new GraphContentFactory(instance));
    StreamToRelationOp<Graph, Graph> w2 =
            new CSPARQLStreamToRelationOp<>(
                    RDFUtils.createIRI("w2"),
                    600_000,
                    60_000,
                    instance,
                    tick,
                    report,
                    report_grain,
                    new GraphContentFactory(instance));
    StreamToRelationOp<Graph, Graph> w3 =
            new CSPARQLStreamToRelationOp<>(
                    RDFUtils.createIRI("w3"),
                    60*60_000,
                    60_000,
                    instance,
                    tick,
                    report,
                    report_grain,
                    new GraphContentFactory(instance));

    // R2R
    VarOrTerm s = new VarImpl("?person");
    VarOrTerm p = new TermImpl("http://test/isIn");
    VarOrTerm o = new VarImpl("?room");
    TP tp = new TP(s, p, o);

    // Define a filter that filters out all the greens
    DatalogR2R datalogR2R = new DatalogR2R();
    ReasonerTriple head = new ReasonerTriple("?x", "http://test/isIn", "?room");
    ReasonerTriple body1 = new ReasonerTriple("?x", "http://test/isWith", "?y");
    ReasonerTriple body2 = new ReasonerTriple("?y", "http://test/isIn", "?room");

    Rule r = new Rule(head,body1,body2);

    datalogR2R.addRule(r);

    // Create a pipe of two r2r operators, datalog reasoner and TP
    R2RPipe<Graph,Binding> r2r = new R2RPipe<>(datalogR2R,tp);


    TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
        new TaskAbstractionImpl.TaskBuilder()
            .addS2R("http://test/observations", w1, "http://test/window")
            .addS2R("http://test/tracing", w2, "http://test/window2")
            .addS2R("http://test/testResults", w3, "http://test/window3")
            .addR2R(List.of("w1","w2"), r2r)
            .addR2S("out", new Rstream<Binding, Binding>())
            .build();
    ContinuousProgram<Graph, Graph, Binding, Binding> cp =
        new ContinuousProgram.ContinuousProgramBuilder()
               .in(observationStream)
                .in(tracingStream)
                .in(covidStream)
                .addTask(t)
            .out(outStream)
            .build();

    outStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
    generator.startStreaming();
    Thread.sleep(20_000);
    generator.stopStreaming();
  }
}
