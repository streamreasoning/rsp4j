package org.streamreasoning.rsp4j.io.utils.websockets;

import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.streamreasoning.rsp4j.io.sinks.AbstractWebsocketSink;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j
@WebSocket
public class WebSocketOutputHandler<T> {

    private Map<Session, WebSocketRemoteConsumer<T>> sessionMap;
    private AbstractWebsocketSink<T> sink;

    public WebSocketOutputHandler(AbstractWebsocketSink<T> sink) {
        this.sessionMap = new HashMap<Session, WebSocketRemoteConsumer<T>>();
        this.sink = sink;
    }

    @OnWebSocketConnect
    public void connected(Session session) {

        log.debug("Client connected");
        if (!sessionMap.containsKey(session)) {
            //Create new remote consumer
            WebSocketRemoteConsumer<T> remoteConsumer = new WebSocketRemoteConsumer<T>(session, sink.getSerializationStrategy());
            sessionMap.put(session, remoteConsumer);
            sink.addConsumer(remoteConsumer);
        }
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        log.debug("Client disconnecting");
        if (sessionMap.containsKey(session)) {
            WebSocketRemoteConsumer<T> remoteConsumer = sessionMap.get(session);
            sessionMap.remove(session);
            //notify Websocket stream
            sink.removeConsumer(remoteConsumer);
        }
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {

    }

    public void close() {
        for (Session session : sessionMap.keySet()) {
            session.close();
        }
    }

}