package org.streamreasoning.rsp;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;


@WebSocket
public class PublishedExample {

    public static void main(String[] args) throws Exception {

        SLD.WebDataStream<String> stream = SLD.<String>fetch("http://localhost:4567/colours");

        System.err.println(stream.describe());

        SLD.Distribution<String>[] distribution = stream.distribution();

        distribution[0].start(i -> "Colour: " + i);
        stream.addConsumer((arg, ts) -> {System.out.println(arg + " " + ts);});

        SLD.Publisher publisher1 = stream.publisher();

        Thread.sleep(1_000); //Needed to wait for WS to establish connection


    }


    @OnWebSocketConnect
    public void connected(Session session) {
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
    }

    @OnWebSocketMessage
    //TODO this is the method that represents await
    public void message(Session session, String message) throws IOException {
        System.out.println(message);
    }


}
