package org.streamreasoning.rsp4j.io.sinks;


import lombok.extern.log4j.Log4j;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketOutputHandler;

import java.net.URI;

/**
 * A Websocket Sink that function as a client, i.e. it can connect to a remote websocket server.
 * This class is wrapper around the WebStream providing WebSocket functionality.
 * It uses a StringSerializationStrategy to convert object of type T to strings for transmission through the websocket channel.
 *
 * @param <T> output type of the sink
 */
@Log4j
public class WebsocketClientSink<T> extends AbstractWebsocketSink<T> {


    protected String wsUrl;

    /**
     * Creates a new Websocket client that functions as a Sink
     *
     * @param stream_uri            the uri of the WebStream, this is only used for linking to the WebStream internally
     * @param wsUrl                 the url of the websocket server to connect to (e.g. ws://localhost:9000/test)
     * @param serializationStrategy the serialization strategy to convert received strings to objects of type T
     */
    public WebsocketClientSink(String stream_uri, String wsUrl, StringSerializationStrategy<T> serializationStrategy) {
        super(stream_uri);
        this.wsUrl = wsUrl;
        this.serializationStrategy = serializationStrategy;
    }

    /**
     * Creates a new Websocket client that functions as a Sink and uses the wsURL as stream uri
     *
     * @param wsUrl                 the url of the websocket server to connect to (e.g. ws://localhost:9000/test), this is also used as stream uri
     * @param serializationStrategy the serialization strategy to convert received strings to objects of type T
     */
    public WebsocketClientSink(String wsUrl, StringSerializationStrategy<T> serializationStrategy) {
        this(wsUrl, wsUrl, serializationStrategy);
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
