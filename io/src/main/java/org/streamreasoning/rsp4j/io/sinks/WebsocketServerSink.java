package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;
import org.streamreasoning.rsp4j.io.utils.websockets.WebSocketOutputHandler;
import spark.Service;

import java.util.ArrayList;
import java.util.List;

public class WebsocketServerSink<T> extends AbstractWebsocketSink<T> {

    private final int port;
    private  WebSocketOutputHandler socket;
    private  String wsURL;
    protected List<Consumer<T>> consumers = new ArrayList<>();
    private Service ws;



    public WebsocketServerSink(String streamURI,int port, String wsURL, StringSerializationStrategy<T> serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
        this.wsURL = wsURL;
        this.port = port;
        this.stream_uri = streamURI;
    }
    public WebsocketServerSink(int port, String wsURL, StringSerializationStrategy<T> serializationStrategy) {
        this(wsURL,port,wsURL,serializationStrategy);
    }

    @Override
    public void startSocket() {
        this.ws = Service.ignite()
                .port(port);

        this.socket = new WebSocketOutputHandler(this);

        ws.webSocket("/"+wsURL, socket);
        ws.init();
    }
    public void stopSocket(){
        this.ws.stop();
    }

}
