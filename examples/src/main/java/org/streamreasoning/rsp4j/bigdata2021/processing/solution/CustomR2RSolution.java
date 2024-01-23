package org.streamreasoning.rsp4j.bigdata2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.RSPEngine;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.table.BindingStream;
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
import org.streamreasoning.rsp4j.reasoning.datalog.TripleGenerator;
import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;

import java.util.List;

/***
 * In this exercise, we will show how to create custom R2R operators, by combining two existing R2R operators.
 * We will pipeline the BGP pattern with a reasoning component that infers the location of individuals reported throug
 * the contact tracing stream.
 *
 * When we know that (:bob :isWith :elena) and (:elena :isIn :redRoom) we can infer that :bob is also in the :redRoom
 * Graph representation:
 *  :bob -isWith-> :elena -isIn-> :redRoom
 *   \                             ^
 *    \----inferred :isIn---------/
 *
 * By inferring this relation, we can simplify the query definitions when we want to fetch who is in each room.
 * This comes in handy when want to query who has been infected because they were in a room with a person who got
 * a positive test result.
 *
 *   This means that you will need to check:
 *    1) All infected individuals
 *    2) The location of these infected individuals
 *    3) All individuals that were in the same room as the infected ones
 *
 *  TIP:
 *  To easily fetch the location of all individuals, you can define a rule that infers the locations of the individuals
 *  reported through the contact tracing stream. Note that the rule needs to be execute over both the contact tracing
 *  and observation stream.
 *
 * Used prefixes:
 *   PREFIX : <http://rsp4j.io/covid/>
 */
public class CustomR2RSolution {

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
            .addTP("?s", ":isIn", "?o")
            .addTP("?s2",":isIn", "?o")
            .build();

    BGP bgp2 = BGP.createWithPrefixes(prefixes)
            .addTP("?testResult", "a", ":TestResultPost")
            .addTP("?testResult",":who", "?s")
            .addTP("?testResult",":hasResult",":positive")
            .build();

    // Define a rule that infers the location of individuals reported through the contact tracing stream
    DatalogR2R datalogR2R = new DatalogR2R();
    TripleGenerator tripleGenerator = new TripleGenerator(prefixes);

    ReasonerTriple head = tripleGenerator.createReasonerTriple("?x", ":isIn", "?room");
    ReasonerTriple body1 = tripleGenerator.createReasonerTriple("?x", ":isWith", "?y");
    ReasonerTriple body2 = tripleGenerator.createReasonerTriple("?y", ":isIn", "?room");

    Rule r = new Rule(head,body1,body2);

    datalogR2R.addRule(r);

    // Create a pipe of two r2r operators, datalog reasoner and BGP
    R2RPipe<Graph,Binding> r2r = new R2RPipe<>(datalogR2R,bgp);

    TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
            new TaskOperatorAPIImpl.TaskBuilder(prefixes)
                    .addS2R(":observations", w1, "w1")
                    .addS2R(":tracing", w2, "w2")
                    .addS2R(":testResults", w3, "w3")
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
