package org.streamreasoning.rsp4j.bigdata2021.processing.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.QueryTaskAbstractionImpl;
import org.streamreasoning.rsp4j.abstraction.TaskAbstractionImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.bigdata2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

/***
 * In this exercise we will learn how to query the covid streams using RSPQL
 * We will define a query that checks who is with who and in which room,
 * in the last 10 minutes and provide answers each minute
 *
 * Used prefixes:
 *  PREFIX : <http://rsp4j.io/covid/>
 *  PREFIX rsp4j: <http://rsp4j.io/>
 */
public class QueryProcessingExample {

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


    // Define the query that checks who is with who and in which room in the last 10 minutes and provide answers each minute
    ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
                "PREFIX : <http://rsp4j.io/covid/> "
                + "PREFIX rsp4j: <http://rsp4j.io/>"
                + "REGISTER RSTREAM <http://out/stream> AS "
                + "SELECT ?s ?o ?s2 "
                + "FROM NAMED WINDOW rsp4j:window ON :observations [RANGE PT10M STEP PT1M] "
                + "FROM NAMED WINDOW rsp4j:window2 ON :tracing [RANGE PT10M STEP PT1M] "
                + "WHERE {"
                + "   WINDOW rsp4j:window { ?s :isIn ?o .}"
                + "   WINDOW rsp4j:window2 { ?s2 :isWith ?s .}"
                    + "}");

    // Create the RSP4J Task and Continuous Program
    TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
        new QueryTaskAbstractionImpl.QueryTaskBuilder().fromQuery(query).build();
    ContinuousProgram<Graph, Graph, Binding, Binding> cp =
        new ContinuousProgram.ContinuousProgramBuilder()
            .in(observationStream)
            .in(tracingStream)
            .in(covidStream)
            .addTask(t)
            .out(query.getOutputStream())
            .addJoinAlgorithm(new HashJoinAlgorithm())
            .build();
    // Add the Consumer to the stream
    query.getOutputStream().addConsumer((el, ts) -> System.out.println(el + " @ " + ts));

    // Start streaming
    generator.startStreaming();

    // Stop streaming after 20s
    Thread.sleep(20_000);
    generator.stopStreaming();
  }
}
