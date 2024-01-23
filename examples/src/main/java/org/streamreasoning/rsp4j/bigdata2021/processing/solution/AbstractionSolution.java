package org.streamreasoning.rsp4j.bigdata2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.RSPEngine;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.table.BindingStream;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.bigdata2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;

import java.util.List;

/***
 * In this exercies, we will build an RSP engine by defining the different operators.
 * We are going to define the operators to mimic the behaviour of the following query:
 *
 *                    PREFIX covid: <http://rsp4j.io/covid/>
 *                    PREFIX : <http://rsp4j.io/>
 *                    REGISTER RSTREAM <http://out/stream> AS
 *                    SELECT ?s ?o ?s2
 *                    FROM NAMED WINDOW :window ON covid:observations [RANGE PT10M STEP PT1M]
 *                    FROM NAMED WINDOW :window2 ON covid:tracing [RANGE PT10M STEP PT1M]
 *                    FROM NAMED WINDOW :window3 ON covid:testResults [RANGE PT24H STEP PT1M]
 *                    WHERE {
 *                       WINDOW :window { ?s covid:isIn ?o .}
 *                       WINDOW :window2 { ?s2 covid:isWith ?s .}
 *                       WINDOW :window3 { ?testResult a covid:TestResultPost; covid:who ?s3; covid:hasResult covid:positive .}
 *                       FILTER(?s3 = ?s || ?s3 = ?s2).
 *                    }
 */
public class AbstractionSolution {

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
            RDFUtils.createIRI("window1"),
            10*60*1000, // window width in milliseconds
            60*1000);   // window  slide in milliseconds

    StreamToRelationOp<Graph, Graph> w2 = engine.createCSparqlWindow(
            RDFUtils.createIRI("window2"),
            10*60*1000, // window width in milliseconds
            60*1000);   // window slide in milliseconds

    StreamToRelationOp<Graph, Graph> w3 = engine.createCSparqlWindow(
            RDFUtils.createIRI("window3"),
            24*60*60*1000, // window width in milliseconds
            60*1000);   // window slide in milliseconds

    // Definition of the prefixes
    PrefixMap prefixes = new PrefixMap();
    prefixes.addPrefix("","http://rsp4j.io/covid/");

    // Definition of the R2R operators
    // BGP for window 1
    BGP bgp = BGP.createWithPrefixes(prefixes)
            .addTP("?s", ":isIn", "?o")
            .build();
    // BGP for window 2
    BGP bgp2 = BGP.createWithPrefixes(prefixes)
            .addTP("?s2", ":isWith", "?s")
            .build();
    // BGP for window 3
    // ?testResult a covid:TestResultPost; covid:who ?s3; covid:hasResult covid:positive
    BGP bgp3 = BGP.createWithPrefixes(prefixes)
            .addTP("?testResult", "a", ":TestResultPost")
            .addTP("?testResult", ":who", "?s3")
            .addTP("?testResult", ":hasResult", ":positive")
            .build();
    //Filter definition for FILTER(?s3 = ?s || ?s3 = ?s2).
    Filter<Binding> filter = new Filter<>(b->b.value("?s3").equals(b.value("?s")) ||
            b.value("?s3").equals(b.value("?s2")));

    // Create the RSP4J Task and Continuous Program that counts the number of s variables
    TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
            new TaskOperatorAPIImpl.TaskBuilder(prefixes)
                    .addS2R(":observations", w1, "window1")
                    .addS2R(":tracing", w2, "window2")
                    .addS2R(":testResults", w3, "window3")

                    .addR2R("window1", bgp)
                    .addR2R("window2", bgp2)
                    .addR2R("window3", bgp3)
                    .addR2R("default", new R2RPipe(filter))
                    .addR2S("out", new Rstream<Binding, Binding>())
                    .addProjectionStrings(List.of("?s","?o","?s2","?s3"))
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
