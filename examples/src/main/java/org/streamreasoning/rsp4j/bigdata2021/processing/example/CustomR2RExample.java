package org.streamreasoning.rsp4j.bigdata2021.processing.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.RSPEngine;
import org.streamreasoning.rsp4j.abstraction.TaskAbstractionImpl;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
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
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

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

    RSPEngine engine = new RSPEngine(instance, tick, report_grain, report);
    // Window (S2R) declaration incl. window name, window range (1s), window step (1s), start time
    // (instance) etc.
    StreamToRelationOp<Graph, Graph> w1 = engine.createCSparqlWindow(
                    RDFUtils.createIRI("w1"),
                    600_000,
                    60_000);

    StreamToRelationOp<Graph, Graph> w2 = engine.createCSparqlWindow(
                    RDFUtils.createIRI("w2"),
                    600_000,
                    60_000);
    StreamToRelationOp<Graph, Graph> w3 = engine.createCSparqlWindow(
                    RDFUtils.createIRI("w3"),
                    60*60_000,
                    60_000);

    // R2R
    BGP bgp = BGP.createFrom("?s", "http://rsp4j.io/covid/isIn", "?o")
            .join("?s2","http://rsp4j.io/covid/isIn", "?o")
            .create();

    BGP bgp2 = BGP.createFrom("?testResult", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://rsp4j.io/covid/TestResultPost")
            .join("?testResult","http://rsp4j.io/covid/who", "?s")
            .join("?testResult","http://rsp4j.io/covid/hasResult","http://rsp4j.io/covid/positive")
            .create();

    // Define a filter that filters out all the greens
    DatalogR2R datalogR2R = new DatalogR2R();
    ReasonerTriple head = new ReasonerTriple("?x", "http://rsp4j.io/covid/isIn", "?room");
    ReasonerTriple body1 = new ReasonerTriple("?x", "http://rsp4j.io/covid/isWith", "?y");
    ReasonerTriple body2 = new ReasonerTriple("?y", "http://rsp4j.io/covid/isIn", "?room");

    Rule r = new Rule(head,body1,body2);

    datalogR2R.addRule(r);

    // Create a pipe of two r2r operators, datalog reasoner and BGP
    R2RPipe<Graph,Binding> r2r = new R2RPipe<>(datalogR2R,bgp);

    TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
        new TaskAbstractionImpl.TaskBuilder()
            .addS2R("http://rsp4j.io/covid/observations", w1, "w1")
            .addS2R("http://rsp4j.io/covid/tracing", w2, "w2")
            .addS2R("http://rsp4j.io/covid/testResults", w3, "w3")
            .addR2R(List.of("w1", "w2"), r2r)
            .addR2R("w3", bgp2)
            .addR2S("out", new Rstream<Binding, Binding>())
            //.addProjection(List.of(new VarImpl("?o")))
            .build();
    ContinuousProgram<Graph, Graph, Binding, Binding> cp =
        new ContinuousProgram.ContinuousProgramBuilder()
               .in(observationStream)
                .in(tracingStream)
                .in(covidStream)
                .addTask(t)
            .out(outStream)
                .addJoinAlgorithm(new HashJoinAlgorithm())
            .build();

    outStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
    generator.startStreaming();
    Thread.sleep(20_000);
    generator.stopStreaming();
  }
}
