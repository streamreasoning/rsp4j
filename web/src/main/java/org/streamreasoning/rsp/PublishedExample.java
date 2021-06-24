package org.streamreasoning.rsp;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;


@WebSocket
public class PublishedExample {

    public static void main(String[] args) throws Exception {

//        SLD.WebDataStream<String> stream = SLD.<String>fetch("http://localhost:4567/colours");
//
//        SLD.Distribution<String>[] distribution = stream.distribution();
//
//        distribution[0].start(parseString -> new ParsingResult<String>(parseString, System.currentTimeMillis())); //starts the thread that allows the internal consumption
//
//        stream.addConsumer((arg, ts) -> {System.out.println(arg + " " + ts);});
//
//        SLD.Publisher publisher1 = stream.publisher();

        WebSocketClient client = new WebSocketClient();


        client.start();
        client.connect(new PublishedExample(), new URI("ws://127.0.0.1:1234/"));


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
