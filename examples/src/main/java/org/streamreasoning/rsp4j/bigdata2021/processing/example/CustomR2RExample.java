package org.streamreasoning.rsp4j.bigdata2021.processing.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.RSPEngine;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.table.BindingStream;
import org.streamreasoning.rsp4j.reasoning.datalog.TripleGenerator;
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
import org.streamreasoning.rsp4j.reasoning.datalog.DatalogR2R;
import org.streamreasoning.rsp4j.reasoning.datalog.ReasonerTriple;
import org.streamreasoning.rsp4j.reasoning.datalog.Rule;
import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;
import org.streamreasoning.rsp4j.yasper.querying.operators.Istream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;

import java.util.List;

/***
 * In this example, we will show how to create custom R2R operators, by combining two existing R2R operators.
 * We will pipeline the BGP pattern with a reasoning component that adds the parent type of the different Observations
 * and Posts.
 *
 * The hierarchy of posts looks as follows:
 *
 *                                      :Observation
 *                                           ^
 *                                           |
 *                   ------------------------------------------------------
 *                   |              |                  |                  |
 *         :RFIDObservation    :FacebookPost    :ContactTracingPost    :TestResultPost
 *
 *
 * By taking this hierarchy into account, we can query for all :Observations at once, without the need to differentiate
 * between different types of observations/posts.
 *
 * Used prefixes:
 *   PREFIX : <http://rsp4j.io/covid/>
 */
public class CustomR2RExample {

  public static void main(String[] args) throws InterruptedException {
    // Setup the stream generator
    StreamGenerator generator = new StreamGenerator();
    /* Creates the observation stream
     * Contains both the RFIDObservations and FacebookPosts
     * IRI: http://rsp4j.io/covid/observations
     *
     * Example RFID observation:
     *  :observationX a :RFIDObservation .
     *  :observationX :who :Alice .
     *  :observationX :where :RedRoom .
     *  :Alice :isIn :RedRoom .
     *
     * Example Facebook Post checkin:
     * :postY a :FacebookPost .
     * :postY :who :Bob .
     * :postY :where :BlueRoom .
     * :Bob :isIn :BlueRoom .
     */
    DataStream<Graph> observationStream = generator.getObservationStream();
    /* Creates the contact tracing stream
     * Describes who was with whom
     * IRI: http://rsp4j.io/covid/tracing
     *
     * Example contact post:
     * :postZ a :ContactTracingPost .
     * :postZ :who :Carl.
     * :Carl :isWith :Bob .
     */
    DataStream<Graph> tracingStream = generator.getContactStream();
    /* Creates the covid results stream
     * Contains the test results
     * IRI: http://rsp4j.io/covid/testResults
     *
     * Example covid result:
     * :postQ a :TestResultPost.
     * :postQ :who :Carl .
     * :postQ :hasResult :positive
     */
    DataStream<Graph> covidStream = generator.getCovidStream();

    // define output stream
    BindingStream outStream = new BindingStream("out");

    // Engine properties
    Report report = new ReportImpl();
    report.add(new OnWindowClose());
    Tick tick = Tick.TIME_DRIVEN;
    ReportGrain report_grain = ReportGrain.SINGLE;
    Time instance = new TimeImpl(0);

    RSPEngine engine = new RSPEngine(instance, tick, report_grain, report);
    // Window (S2R) declaration
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

    PrefixMap prefixes = new PrefixMap();
    prefixes.addPrefix("","http://rsp4j.io/covid/");
    // R2R
    BGP bgp = BGP.createWithPrefixes(prefixes)
            .addTP("?obs", "a", ":Observation")
            .build();


    // Define a simple Reasoning component that
    DatalogR2R datalogR2R = new DatalogR2R();
    TripleGenerator tripleGenerator = new TripleGenerator(prefixes);
    // Each part of the hierarchy is defined as a Rule
    // :RFIDObservation -> :Observation
    ReasonerTriple body = tripleGenerator.createReasonerTriple("?x", "a", ":RFIDObservation");
    ReasonerTriple head = tripleGenerator.createReasonerTriple("?x", "a", ":Observation");
    datalogR2R.addRule(new Rule(head,body));

    // :FacebookPost -> :Observation
    ReasonerTriple body2 = tripleGenerator.createReasonerTriple("?x", "a", ":FacebookPost");
    ReasonerTriple head2 = tripleGenerator.createReasonerTriple("?x", "a", ":Observation");
    datalogR2R.addRule(new Rule(head2,body2));

    // :TestResultPost -> :Observation
    ReasonerTriple body3 = tripleGenerator.createReasonerTriple("?x", "a", ":TestResultPost");
    ReasonerTriple head3 = tripleGenerator.createReasonerTriple("?x", "a", ":Observation");
    datalogR2R.addRule(new Rule(head3,body3));

    // :ContactTracingPost -> :Observation
    ReasonerTriple body4 = tripleGenerator.createReasonerTriple("?x", "a", ":ContactTracingPost");
    ReasonerTriple head4 = tripleGenerator.createReasonerTriple("?x", "a", ":Observation");
    datalogR2R.addRule(new Rule(head4,body4));

    // Create a pipe of two r2r operators, datalog reasoner and BGP
    R2RPipe<Graph,Binding> r2r = new R2RPipe<>(datalogR2R,bgp);

    TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
        new TaskOperatorAPIImpl.TaskBuilder(prefixes)
            .addS2R(":observations", w1, "w1")
            .addS2R(":tracing", w2, "w2")
            .addS2R(":testResults", w3, "w3")
            .addR2R(List.of("w1", "w2","w3"), r2r)
            .addR2S("out", new Istream<Binding, Binding>())
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
