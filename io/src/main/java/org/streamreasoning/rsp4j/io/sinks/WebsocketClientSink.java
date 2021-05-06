package org.streamreasoning.rsp4j.io.sinks;


import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketInputHandler;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketOutputHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class WebsocketClientSink<T> extends AbstractWebsocketSink<T> {


    protected String wsUrl;

    public WebsocketClientSink(String stream_uri, String wsUrl, StringSerializationStrategy<T> serializationStrategy){
        this.stream_uri = stream_uri;
        this.wsUrl = wsUrl;
        this.serializationStrategy = serializationStrategy;
    }
    public WebsocketClientSink(String wsUrl, StringSerializationStrategy<T> serializationStrategy){
        this(wsUrl,wsUrl,serializationStrategy);
    }


    @Override
    public void startSocket() {
        WebSocketClient client = new WebSocketClient();

        WebSocketOutputHandler<T> socket = new WebSocketOutputHandler<T>(this);
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




}
