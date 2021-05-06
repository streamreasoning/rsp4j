package org.streamreasoning.rsp4j.io.sources;



import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketInputHandler;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class WebsocketClientSource<T> implements WebDataStream<T> {

    private ParsingStrategy<T> parsingStrategy;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    protected String wsUrl;
    protected String stream_uri;

    public WebsocketClientSource(String stream_uri, String wsUrl, ParsingStrategy<T> parsingStrategy){
        this.stream_uri = stream_uri;
        this.wsUrl = wsUrl;
        this.parsingStrategy = parsingStrategy;
    }
    public WebsocketClientSource(String wsUrl, ParsingStrategy<T> parsingStrategy){
        this(wsUrl,wsUrl,parsingStrategy);
    }

    public void stream() {
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
    public String uri() {
        return stream_uri;
    }


}
