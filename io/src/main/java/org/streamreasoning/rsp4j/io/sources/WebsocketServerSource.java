package org.streamreasoning.rsp4j.io.sources;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketInputHandler;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketOutputHandler;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

public class WebsocketServerSource<T> implements WebDataStream<T> {


    private final WebSocketInputHandler socket;
    private final String wsURL;
    private final ParsingStrategy<T> parsingStrategy;
    private final Service ws;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    private final String stream_uri;
    public WebsocketServerSource(String streamURI,int port,String wsURL, ParsingStrategy<T> parsingStrategy) {
        this.stream_uri = streamURI;
        this.parsingStrategy = parsingStrategy;
        this.ws = Service.ignite()
                .port(port);

        this.socket = new WebSocketInputHandler(this,parsingStrategy);
        this.wsURL = wsURL;
        ws.webSocket("/"+wsURL, socket);
        ws.init();

    }
    public WebsocketServerSource(int port,String wsURL, ParsingStrategy<T> parsingStrategy) {
        this(wsURL,port,wsURL,parsingStrategy);
    }

    public void stopSocket(){
        this.ws.stop();
    }
    @Override
    public void addConsumer(Consumer<T> c) {
        consumers.add(c);
    }

    @Override
    public void put(T e, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(e, ts));

    }

    @Override
    public String uri() {
        return this.stream_uri;
    }
}
