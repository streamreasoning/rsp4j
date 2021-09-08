package org.streamreasoning.rsp4j.cqels.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.cqels.CQELSEngineRSP4J;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

public class RSP4JCQELSSelectExample {

    public static void main(String[] args) throws InterruptedException {
        // Setup the stream generator
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        DataStream<Binding> outputStream = new DataStreamImpl<>("http://out/stream");


        String query1 = "Select * WHERE {"
                + "STREAM <http://test/stream> [RANGE 15s] {?s ?p ?o .}"
                + "}";


        CQELSEngineRSP4J cqels = new CQELSEngineRSP4J();
        cqels.register(inputStream);
        cqels.setSelectOutput(outputStream);

        ContinuousQuery<Graph, Binding, Binding, Binding> cq = cqels.parseCQELSSelect(query1);

        ContinuousQueryExecution<Graph, Binding, Binding, Binding> cqe = cqels.parseSelect(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));

        generator.startStreaming();
        Thread.sleep(20_000);
        generator.stopStreaming();
    }
}
