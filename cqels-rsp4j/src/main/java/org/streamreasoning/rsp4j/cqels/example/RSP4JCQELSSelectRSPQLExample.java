package org.streamreasoning.rsp4j.cqels.example;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.cqels.CQELSEngineRSP4J;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

public class RSP4JCQELSSelectRSPQLExample {

    public static void main(String[] args) throws InterruptedException {
        // Setup the stream generator
//        StreamGenerator generator = new StreamGenerator();
//        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
//        DataStream<Binding> outputStream = new DataStreamImpl<>("http://out/stream");
//
//
//        String query1 = "Select * WHERE {"
//                + "STREAM <http://test/stream> [RANGE 15s] {?s ?p ?o .}"
//                + "}";
//
//    query1 =
//            "PREFIX  :     <http://debs2015.org/streams/>\n"
//            + "\n"
//            + "REGISTER STREAM <http://out/stream> AS\n"
//            + "\n"
//            + "SELECT *\n"
//            + "FROM NAMED WINDOW :win ON <http://test/stream> [RANGE PT1S STEP PT1S]\n"
//            + "WHERE\n"
//            + "  { WINDOW :win\n"
//            + "      { ?s ?p ?o\n"
//            + "      }\n"
//            + "  }";
//
//        CQELSEngineRSP4J cqels = new CQELSEngineRSP4J();
//        cqels.register(inputStream);
//        cqels.setSelectOutput(outputStream);
//
//        ContinuousQuery<Graph, Binding, Binding, Binding> cq = cqels.parseRSPQLSelect(query1);
//
//        ContinuousQueryExecution<Graph, Binding, Binding, Binding> cqe = cqels.parseSelect(cq);
//
//
//        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
//
//        generator.startStreaming();
//        Thread.sleep(20_000);
//        generator.stopStreaming();
    }
}
