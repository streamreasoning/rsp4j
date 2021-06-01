package org.streamreasoning.rsp;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;

import java.util.ArrayList;
import java.util.List;

@WebSocket
public class WebSocketHandler<E> implements Consumer<E> {

    List<Session> sessionList = new ArrayList<>();

    @OnWebSocketConnect
    public void onConnect(Session s) throws Exception {
        sessionList.add(s);
        s.getRemote().sendString("Hello Colours");
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        sessionList.remove(user);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        sessionList.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void notify(E arg, long ts) {
        onMessage(null, arg.toString());
    }
}
