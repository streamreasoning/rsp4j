package org.streamreasoning.rsp4j.bigdata2021.processing.solution;

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
 * In this exercise we will learn how to query the color stream using RSPQL
 */
public class QueryProcessingSolution {

  public static void main(String[] args) throws InterruptedException {
    // Setup the stream generator
    org.streamreasoning.rsp4j.bigdata2021.utils.StreamGenerator generator = new StreamGenerator();
    DataStream<Graph> observationStream = generator.getObservationStream();
    DataStream<Graph> tracingStream = generator.getContactStream();
    DataStream<Graph> covidStream = generator.getCovidStream();


    // Define the query consisting of variables only (?s ?o ?p) with a window of 1s range and 1s step and count the number of ?s
    ContinuousQuery<Graph, Graph, Binding, Binding> query =
            TPQueryFactory.parse(
                    ""
                            + "REGISTER RSTREAM <http://out/stream> AS "
                            + "SELECT * "
                            + "FROM NAMED WINDOW <http://test/window> ON <http://rsp4j.io/covid/observations> [RANGE PT10M STEP PT1M] "
                            + "FROM NAMED WINDOW <http://test/window2> ON <http://rsp4j.io/covid/tracing> [RANGE PT10M STEP PT1M] "
                            + "FROM NAMED WINDOW <http://test/window3> ON <http://rsp4j.io/covid/testResults> [RANGE PT1H STEP PT1M] "
                            + "WHERE {"
                            + "   WINDOW <http://test/window> { ?s <http://rsp4j.io/covid/isIn> ?o .}"
                            + "   WINDOW <http://test/window2> { ?s2 <http://rsp4j.io/covid/isWith> ?s .}"
                            + "   WINDOW <http://test/window3> { ?covidTest <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://rsp4j.io/covid/TestResultPost>; <http://rsp4j.io/covid/who> ?s}"

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
