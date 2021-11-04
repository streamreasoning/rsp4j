package org.streamreasoning.rsp4j.csparql;

import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.core.engine.ConsoleFormatter;
import eu.larkc.csparql.core.engine.CsparqlEngine;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestCSPARQL {


    public void testCSPARQL() throws ParseException, InterruptedException {
        CsparqlEngine engine = new CsparqlEngineImpl();
        engine.initialize(true);
        String query = "REGISTER QUERY WhoLikesWhat AS "
                + "PREFIX ex: <http://myexample.org/> "
                //+ "CONSTRUCT {?s ex:test ?o} "
                + "Select ?s ?o "
                + "FROM STREAM <http://myexample.org/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s ex:likes ?o }";

        RdfStream myStream = new MyRDFStream("http://myexample.org/stream");
        engine.registerStream(myStream);
        final Thread t = new Thread((Runnable) myStream);
        t.start();

        CsparqlQueryResultProxy c1 = engine.registerQuery(query, false);

        if (c1 != null) {
            c1.addObserver(new ConsoleFormatter());
        }

        Thread.sleep(10_000);
    }

    @Test
    public void testRSP4JCSPARQLSelect() throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        DataStream<Binding> outputStream = new DataStreamImpl<>("http://out/stream");



        String query1 = "REGISTER QUERY GetColours AS "
                + "PREFIX ex: <http://myexample.org/> "
                + "SELECT ?s ?p ?o "
                + "FROM STREAM <http://test/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s ?p ?o }";


        CSPARQLEngineRSP4J csparql = new CSPARQLEngineRSP4J();
        csparql.register(inputStream);
        csparql.setSelectOutput(outputStream);

        ContinuousQuery<Graph, Graph, Binding, Binding> cq = csparql.parseCSPARQLSelect(query1);

        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = csparql.parseSelect(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
        List<Object> resultCounter = new ArrayList<>();
        outputStream.addConsumer((el,ts)->resultCounter.add(el));
        generator.startStreaming();
        Thread.sleep(3_000);
        generator.stopStreaming();
        assertTrue(resultCounter.size()>0);
    }

    @Test
    public void testRSP4JCSPARQLConstruct() throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        DataStream<Graph> outputStream = new DataStreamImpl<>("http://out/stream");



        String query1 = "REGISTER QUERY GetColours AS "
                + "PREFIX ex: <http://myexample.org/> "
                + "CONSTRUCT { ?s ?p ?o }"
                + "FROM STREAM <http://test/stream> [RANGE 5s STEP 1s] "
                + "WHERE { ?s ?p ?o }";


        CSPARQLEngineRSP4J csparql = new CSPARQLEngineRSP4J();
        csparql.register(inputStream);
        csparql.setConstructOutput(outputStream);

        ContinuousQuery<Graph, Graph, Binding, Graph> cq = csparql.parseCSPARQLConstruct(query1);

        ContinuousQueryExecution<Graph, Graph, Binding, Graph> cqe = csparql.parseConstruct(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
        List<Object> resultCounter = new ArrayList<>();
        outputStream.addConsumer((el,ts)->resultCounter.add(el));
        generator.startStreaming();
        Thread.sleep(3_000);
        generator.stopStreaming();
        assertTrue(resultCounter.size()>0);

    }
    @Test
    public void testRSP4JCSPARQLSelectDataValue() throws InterruptedException {
        DataStream<Graph> inputStream = new DataStreamImpl<>("http://test/stream");
        DataStream<Binding> outputStream = new DataStreamImpl<>("http://out/stream");



        String query1 = "REGISTER QUERY GetColours AS "
                + "PREFIX ex: <http://myexample.org/> "
                + "SELECT ?s ?p ?o "
                + "FROM STREAM <http://test/stream> [RANGE 1s STEP 1s] "
                + "WHERE { ?s ?p ?o }";


        CSPARQLEngineRSP4J csparql = new CSPARQLEngineRSP4J();
        csparql.register(inputStream);
        csparql.setSelectOutput(outputStream);

        ContinuousQuery<Graph, Graph, Binding, Binding> cq = csparql.parseCSPARQLSelect(query1);

        ContinuousQueryExecution<Graph, Graph, Binding, Binding> cqe = csparql.parseSelect(cq);


        outputStream.addConsumer((el,ts)->System.out.println(el + " @ " + ts));
        List<Object> resultCounter = new ArrayList<>();
        outputStream.addConsumer((el,ts)->resultCounter.add(el));
        RDF instance = RDFUtils.getInstance();

        Graph graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("http://test#S1"), instance.createIRI("http://test#hasValue"), instance.createLiteral("56.1519", instance.createIRI("http://www.w3.org/2001/XMLSchema#double"))));
        inputStream.put(graph,System.currentTimeMillis());
        inputStream.put(graph,System.currentTimeMillis());
        Thread.sleep(1000);
        inputStream.put(graph,System.currentTimeMillis());
        inputStream.put(graph,System.currentTimeMillis());
        Thread.sleep(1000);
        inputStream.put(graph,System.currentTimeMillis());
        inputStream.put(graph,System.currentTimeMillis());
        Thread.sleep(2000);
        assertTrue(resultCounter.size()>0);
    }
}
