package org.streamreasoning.rsp4j.wspbook.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.bigdata2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

/***
 * In this example we will learn how to query the covid streams using RSPQL
 * You will define a query that checks who is infected through close contact.
 * Furthermore, you will need to report in which room the contact happened.
 *
 * This means that you will need to check:
 *  1) Who is with who through the contact tracing stream in the last 10minutes
 *  2) The location of certain individuals through the observation stream in last 10minutes
 *  3) Who had a positive test result in the last 24hours
 *
 *  TIP:
 *  As both persons in the contact tracing results (?person1 :isWith ?person2) can be reported in the testResults
 *  stream, we can use a FILTER to create an OR-case.
 *
 * Used prefixes:
 *  PREFIX : <http://rsp4j.io/covid/>
 *  PREFIX rsp4j: <http://rsp4j.io/>
 */
public class QueryProcessingSolution {

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

    // Define the query that checks who is infected through close contact
    ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
            "PREFIX : <http://rsp4j.io/covid/> "
                + " PREFIX rsp4j: <http://rsp4j.io/> "
                + "REGISTER RSTREAM <http://out/stream> AS "
                + "SELECT ?s ?s2 ?o ?s3 "
                + " "
                + " FROM NAMED WINDOW rsp4j:window ON :observations [RANGE PT10M STEP PT1M] "
                + " FROM NAMED WINDOW rsp4j:window2 ON :tracing [RANGE PT10M STEP PT1M] "
                + " FROM NAMED WINDOW rsp4j:window3 ON :testResults [RANGE PT24H STEP PT1M] "
                + "WHERE {"
                + "   WINDOW rsp4j:window { ?s :isIn ?o .}"
                + "   WINDOW rsp4j:window2 { ?s2 :isWith ?s .}"
                + "   WINDOW rsp4j:window3 { ?testResult a :TestResultPost; :who ?s3; :hasResult :positive .}"
                + " FILTER(?s3 = ?s || ?s3 = ?s2)."
                + "}");

    // Create the RSP4J Task and Continuous Program
    TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
            new QueryTaskOperatorAPIImpl.QueryTaskBuilder().fromQuery(query).build();

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
    Thread.sleep(40_000);
    generator.stopStreaming();
  }
}
