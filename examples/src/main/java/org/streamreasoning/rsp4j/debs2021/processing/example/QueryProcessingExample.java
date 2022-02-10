package org.streamreasoning.rsp4j.debs2021.processing.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

/***
 * In this exercise we will learn how to query the color stream using RSPQL
 */
public class QueryProcessingExample {

  public static void main(String[] args) throws InterruptedException {
    // Setup the stream generator
    StreamGenerator generator = new StreamGenerator();
    DataStream<Graph> inputStream = generator.getStream("http://test/stream");

    // Define the query consisting of variables only (?s ?o ?p) with a window of 1s range and 1s step and count the number of ?s
    ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
            ""
                + "REGISTER RSTREAM <http://out/stream> AS "
                + "SELECT (COUNT(?s) AS ?count) "
                + "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT1S] "
                + "WHERE {"
                + "   WINDOW <http://test/window> { ?s ?p ?o .}"
                + "}");

    // Create the RSP4J Task and Continuous Program
    TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
        new QueryTaskOperatorAPIImpl.QueryTaskBuilder().fromQuery(query).build();
    ContinuousProgram<Graph, Graph, Binding, Binding> cp =
        new ContinuousProgram.ContinuousProgramBuilder()
            .in(inputStream)
            .addTask(t)
            .out(query.getOutputStream())
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
