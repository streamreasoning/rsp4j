package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketOutputHandler;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A Websocket Sink that function as a server, i.e. remote clients can connect and receive data.
 * This class is wrapper around the WebStream providing WebSocket functionality.
 * It uses a StringSerializationStrategy to convert object of type T to strings for transmission through the websocket channel.
 *
 * @param <T>  output type of the sink
 */
public class WebsocketServerSink<T> extends AbstractWebsocketSink<T> {

    private final int port;
    private  WebSocketOutputHandler socket;
    private  String wsPath;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    private Service ws;


    /**
     * Creates a new Websocket server that functions as a Sink
     * @param streamURI  the uri of the WebStream, this is only used for linking to the WebStream internally
     * @param port  the port to open the websocket
     * @param wsPath  the path to open the websocket (e.g. test which becomes ws://localhost:<port>/<path>)
     * @param serializationStrategy  the serialization strategy to convert objects of type T to strings
     */
    public WebsocketServerSink(String streamURI,int port, String wsPath, StringSerializationStrategy<T> serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
        this.wsPath = wsPath;
        this.port = port;
        this.stream_uri = streamURI;
    }
    /**
     * Creates a new Websocket server that functions as a Sink, it uses the wsURL as stream uri
     * @param port  the port to open the websocket
     * @param wsPath  the path to open the websocket, this is also used for creating the stream uri (i.e. ws://localhost:<port>/<path>)
     * @param serializationStrategy  the serialization strategy to convert objects of type T to strings
     */
    public WebsocketServerSink(int port, String wsPath, StringSerializationStrategy<T> serializationStrategy) {
        this("ws://localhost:"+port+"/"+ wsPath,port, wsPath,serializationStrategy);
    }

    @Override
    public void startSocket() {
        this.ws = Service.ignite()
                .port(port);

        this.socket = new WebSocketOutputHandler(this);

        ws.webSocket("/"+ wsPath, socket);
        ws.init();
    }
    public void stopSocket(){
        this.ws.stop();
    }

}
