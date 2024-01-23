package org.streamreasoning.rsp4j.io.utils.websockets;

import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.IOException;

@WebSocket
public class WebSocketInputHandler<T> {
    private static final Logger log = Logger.getLogger(WebSocketInputHandler.class);
    private final ParsingStrategy<T> parsingStrategy;
    private DataStream<T> stream;

    public WebSocketInputHandler(DataStream<T> stream, ParsingStrategy<T> parsingStrategy) {
        this.stream = stream;
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
        ParsingResult<T> parsed = parsingStrategy.parseAndAddTime(message);
        stream.put(parsed.getResult(), parsed.getTimeStamp());
    }

    @OnWebSocketError
    public void onWebSocketError(Throwable cause) {
        System.err.println(cause);
    }
}