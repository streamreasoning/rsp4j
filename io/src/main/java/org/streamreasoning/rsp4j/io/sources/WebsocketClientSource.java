package org.streamreasoning.rsp4j.io.sources;


import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketInputHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * A Websocket Source that function as a client, i.e. it can connect to a remote websocket server and receive data.
 * This class is wrapper around the WebStream providing WebSocket functionality.
 * It uses a ParsingStrategy to convert the received strings through the websocket channel to object of type T.
 *
 * @param <T> output type of the sink
 */
public class WebsocketClientSource<T> implements DataStream<T> {

    private static final Logger log = Logger.getLogger(WebsocketClientSource.class);
    protected List<Consumer<T>> consumers = new ArrayList<>();
    protected String wsUrl;
    protected String stream_uri;
    private ParsingStrategy<T> parsingStrategy;

    /**
     * Creates a Websocket client that functions as a Source
     *
     * @param stream_uri      the uri of the WebStream, this is only used for linking to the WebStream internally
     * @param wsUrl           the uri of the remove websocket server
     * @param parsingStrategy the parsing strategy that converts received strings to objects of type T
     */
    public WebsocketClientSource(String stream_uri, String wsUrl, ParsingStrategy<T> parsingStrategy) {
        this.stream_uri = stream_uri;
        this.wsUrl = wsUrl;
        this.parsingStrategy = parsingStrategy;
    }

    /**
     * Creates a Websocket client that functions as a Source, it uses the wsURL as stream uri
     *
     * @param wsUrl           the uri of the remove websocket server, which is also used as stream uri
     * @param parsingStrategy the parsing strategy that converts received strings to objects of type T
     */
    public WebsocketClientSource(String wsUrl, ParsingStrategy<T> parsingStrategy) {
        this(wsUrl, wsUrl, parsingStrategy);
    }

    public void startSocket() {
        WebSocketClient client = new WebSocketClient();

        WebSocketInputHandler<T> socket = new WebSocketInputHandler<T>(this, parsingStrategy);
        try {
            client.start();
            URI echoUri = new URI(this.wsUrl);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, echoUri, request);
            log.debug(String.format("Connecting to : %s%n", echoUri));

        } catch (Throwable t) {
            t.printStackTrace();
        }
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
        return stream_uri;
    }


}
