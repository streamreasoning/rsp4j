package org.streamreasoning.rsp4j.io.utils.websockets;

import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.IOException;
@Log4j
@WebSocket
public class WebSocketInputHandler<T> {
    private final ParsingStrategy<T> parsingStrategy;
    private DataStream<T> stream;

    public WebSocketInputHandler(DataStream<T> stream, ParsingStrategy<T> parsingStrategy) {
        this.stream=stream;
        this.parsingStrategy = parsingStrategy;
    }
    @OnWebSocketConnect
    public void connected(Session session) {
        log.debug("Connected");
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        log.debug(reason);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        log.debug("Received " + message);
        ParsingResult<T> parsed = parsingStrategy.parse(message);
        stream.put(parsed.getResult(),parsed.getTimeStamp());
    }


}