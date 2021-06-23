package org.streamreasoning.rsp4j.io.sources;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketInputHandler;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A Websocket Source that function as a server, i.e. it allows remote clients to connect and send data.
 * This class is wrapper around the WebStream providing WebSocket functionality.
 * It uses a ParsingStrategy to convert the received strings through the websocket channel to object of type T.
 *
 * @param <T>  output type of the sink
 */

public class WebsocketServerSource<T> implements DataStream<T> {


    private final WebSocketInputHandler socket;
    private final String wsPath;
    private final ParsingStrategy<T> parsingStrategy;
    private final Service ws;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    private final String stream_uri;

    /**
     * Creates a Websocket server that functions as a Source
     * @param streamURI  the uri of the WebStream, this is only used for linking to the WebStream internally
     * @param port  the port to open the websocket
     * @param wsPath  the path to open the websocket (e.g. test which becomes ws://localhost:<port>/<path>)
     * @param parsingStrategy  the parsing strategy that converts strings to objects of type T
     */
    public WebsocketServerSource(String streamURI, int port, String wsPath, ParsingStrategy<T> parsingStrategy) {
        this.stream_uri = streamURI;
        this.parsingStrategy = parsingStrategy;
        this.ws = Service.ignite()
                .port(port);

        this.socket = new WebSocketInputHandler(this,parsingStrategy);
        this.wsPath = wsPath;
        ws.webSocket("/"+ wsPath, socket);
        ws.init();

    }
    /**
     * Creates a Websocket server that functions as a Source, it uses the wsURL as stream uri
     *
     * @param port  the port to open the websocket
     * @param wsPath  the path to open the websocket (e.g. test which becomes ws://localhost:<port>/<path>),
     *                this is also used for creating the stream uri (i.e. ws://localhost:<port>/<path>)
     * @param parsingStrategy  the parsing strategy that converts strings to objects of type T
     */
    public WebsocketServerSource(int port, String wsPath, ParsingStrategy<T> parsingStrategy) {
        this(wsPath,port, wsPath,parsingStrategy);
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
    public String getName() {
        return stream_uri;
    }

    public String uri() {
        return this.stream_uri;
    }
}
