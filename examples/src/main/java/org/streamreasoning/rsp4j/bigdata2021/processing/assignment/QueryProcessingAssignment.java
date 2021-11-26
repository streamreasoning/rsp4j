package org.streamreasoning.rsp4j.bigdata2021.processing.assignment;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.QueryTaskAbstractionImpl;
import org.streamreasoning.rsp4j.abstraction.TaskAbstractionImpl;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

/***
 * In this exercise we will learn how to query the color stream using an RSPQL query.
 */
public class QueryProcessingAssignment {

  public static void main(String[] args) throws InterruptedException {
    // Setup the stream generator
    StreamGenerator generator = new StreamGenerator();
    DataStream<Graph> inputStream = generator.getStream("http://test/stream");

    // TODO Define a query that extracts all green colors with a window of 2s range and 2s step and count the number of greens.
    ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
            "" //<- Add your query here
                );

    // Create the RSP4J Task and Continuous Program
    TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
        new QueryTaskAbstractionImpl.QueryTaskBuilder().fromQuery(query).build();
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
