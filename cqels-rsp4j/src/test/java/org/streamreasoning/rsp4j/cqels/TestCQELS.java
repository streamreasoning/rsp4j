package org.streamreasoning.rsp4j.cqels;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestCQELS {

    @Test
    public void testSelectCQELS() throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        DataStream<Binding> outputStream = new DataStreamImpl<>("http://out/stream");


        String query1 = "Select * WHERE {"
                + "STREAM <http://test/stream> [RANGE 15s] {?s ?p ?o .}"
                + "}";


        CQELSEngineRSP4J cqels = new CQELSEngineRSP4J();
        cqels.register(inputStream);
        DataStream<Binding> bindingDataStream = cqels.setSelectOutput(outputStream);

        ContinuousQuery<Graph, Binding, Binding, Binding> cq = cqels.parseCQELSSelect(query1);

        ContinuousQueryExecution<Graph, Binding, Binding, Binding> cqe = cqels.parseSelect(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
        List<Object> resultCounter = new ArrayList<>();
        outputStream.addConsumer((el,ts)->resultCounter.add(el));
        generator.startStreaming();
        Thread.sleep(3_000);
        generator.stopStreaming();
        assertTrue(resultCounter.size()>0);

    }

    @Test
    public void testConstructCQELS() throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        DataStream<Graph> outputStream = new DataStreamImpl<>("http://out/stream");


        String query1 = "CONSTRUCT{?s ?p ?o} WHERE {"
                + "STREAM <http://test/stream> [RANGE 15s] {?s ?p ?o .}"
                + "}";


        CQELSEngineRSP4J cqels = new CQELSEngineRSP4J();
        cqels.register(inputStream);
        cqels.setConstructOutput(outputStream);

        ContinuousQuery<Graph, Binding, Binding, Graph> cq = cqels.parseCQELSConstruct(query1);

        ContinuousQueryExecution<Graph, Binding, Binding, Graph> cqe = cqels.parseConstruct(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
        List<Object> resultCounter = new ArrayList<>();
        outputStream.addConsumer((el,ts)->resultCounter.add(el));
        generator.startStreaming();
        Thread.sleep(3_000);
        generator.stopStreaming();
        assertTrue(resultCounter.size()>0);

    }
}
