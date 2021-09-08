package org.streamreasoning.rsp4j.examples.stream;


import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;


public class WebsocketStream extends RDFStream {

    protected String wsUrl;
    protected String stream_uri;
    public WebsocketStream(String stream_uri, String wsUrl) {
        super(stream_uri);
        this.stream_uri = stream_uri;
        this.wsUrl = wsUrl;
    }

    public static void main(String[] args) {
        WebsocketStream stream = new WebsocketStream("http://test", "ws://localhost:9000/test");
        stream.stream();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stream() {
        WebSocketClient client = new WebSocketClient();

        WebSocketInputStream socket = new WebSocketInputStream(this);
        try {
            client.start();

            URI echoUri = new URI(this.wsUrl);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            System.out.printf("Connecting to : %s%n", echoUri);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @WebSocket
    public class WebSocketInputStream {
        private DataStream<Graph> stream;

        public WebSocketInputStream(DataStream<Graph> stream) {
            this.stream = stream;
        }

        @OnWebSocketConnect
        public void connected(Session session) {
            System.out.println("connecting");
        }

        @OnWebSocketClose
        public void closed(Session session, int statusCode, String reason) {
            System.out.println(reason);
        }

        @OnWebSocketMessage
        public void message(Session session, String message) throws IOException {
            System.out.println("received message " + message);
            Model dataModel = ModelFactory.createDefaultModel();
            try {
                InputStream targetStream = new ByteArrayInputStream(message.getBytes());
                dataModel.read(targetStream, null, "TTL");
                JenaRDF jena = new JenaRDF();
                Graph g1 = jena.asGraph(dataModel);
                stream.put(g1, System.currentTimeMillis());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }
}
