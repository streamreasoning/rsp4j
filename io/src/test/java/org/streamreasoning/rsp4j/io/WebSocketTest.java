package org.streamreasoning.rsp4j.io;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.io.sinks.WebsocketClientSink;
import org.streamreasoning.rsp4j.io.sinks.WebsocketServerSink;
import org.streamreasoning.rsp4j.io.sources.WebsocketClientSource;
import org.streamreasoning.rsp4j.io.sources.WebsocketServerSource;
import org.streamreasoning.rsp4j.io.utils.BufferedConsumer;
import org.streamreasoning.rsp4j.io.utils.ParsingStrategyTest;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.serialization.JenaRDFSerializationStrategy;

import static org.junit.Assert.assertEquals;

public class WebSocketTest {
    public static Graph createGraph(){
        RDF rdf = new SimpleRDF();

        Graph graph = rdf.createGraph();
        IRI subject = rdf.createIRI("http://test/subject");
        IRI property = rdf.createIRI("http://test/property");
        IRI object = rdf.createIRI("http://test/object");
        graph.add(subject, property, object);
        return graph;
    }

    //@Test
    public void testWebSocketClientSource() throws InterruptedException{
        /* creating a websocket server sink */
        // first we create a serialization strategy to convert the Graph objects back to strings (to send them over the ws channel)
        JenaRDFSerializationStrategy serializationStrategy = new JenaRDFSerializationStrategy(RDFBase.NT);
        // next we create a websocket client as sink that uses tha serialization strategy
        WebsocketServerSink<Graph> websocketSink = new WebsocketServerSink<Graph>(9000,"test",serializationStrategy);
        websocketSink.startSocket();

        /* creating a websocket client source */
        // we define a parsing strategy to convert strings to Graph object
        JenaRDFParsingStrategy parsingStrategy = new JenaRDFParsingStrategy(RDFBase.NT);
        // next we create a websocket client as source that uses the parsing strategy
        WebsocketClientSource<Graph> websocketSource = new WebsocketClientSource<Graph>("ws://localhost:9000/test",parsingStrategy);
        websocketSource.startSocket();
        BufferedConsumer<Graph> bufferedConsumer = new BufferedConsumer<>();
        websocketSource.addConsumer(bufferedConsumer);

        //sleep so client can connect to server
        Thread.sleep(1000);

        //create a graph to send
        Graph inputGraph = createGraph();
        //send data to the sink
        websocketSink.put(inputGraph,0l);
        //sleep so data can be send from client to server
        Thread.sleep(1000);

        //check size
        assertEquals(1,bufferedConsumer.getSize());
        //check if same graph was received
        ParsingStrategyTest.compareGraph(inputGraph,bufferedConsumer.getMessage(0));

        // shutdown Websockets
        websocketSink.stopSocket();
    }
    //@Test
    public void testWebSocketServerSource() throws InterruptedException{
        /* creating a websocket server source */
        // first we define a parsing strategy to convert strings to Graph object
        JenaRDFParsingStrategy parsingStrategy = new JenaRDFParsingStrategy(RDFBase.NT);
        // next we create a websocket server as source that uses the parsing strategy
        WebsocketServerSource<Graph> websocketSource = new WebsocketServerSource<Graph>(9000,"test",parsingStrategy);
        /* creating a websocket client sink */
        // we create a serialization strategy to convert the Graph objects back to strings (to send them over the ws channel)
        JenaRDFSerializationStrategy serializationStrategy = new JenaRDFSerializationStrategy(RDFBase.NT);
        // next we create a websocket client as sink that uses the serialization strategy
        WebsocketClientSink<Graph> websocketSink = new WebsocketClientSink<Graph>("ws://localhost:9000/test",serializationStrategy);
        // activate the socket
        websocketSink.startSocket();

        // create a simple consumer
        BufferedConsumer<Graph> bufferedConsumer = new BufferedConsumer<>();
        websocketSource.addConsumer(bufferedConsumer);

        //sleep so client can connect to server
        Thread.sleep(1000);
        //create a graph to send
        Graph inputGraph = createGraph();
        //send data to the sink
        websocketSink.put(inputGraph,0l);
        //sleep so data can be send from client to server
        Thread.sleep(1000);

        //check size
        assertEquals(1,bufferedConsumer.getSize());
        //check if same graph was received
        ParsingStrategyTest.compareGraph(inputGraph,bufferedConsumer.getMessage(0));

        // shutdown Websockets
        websocketSource.stopSocket();
    }

}
