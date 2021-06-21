package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

public abstract class AbstractWebsocketSink<T> extends DataStreamImpl<T> {


    protected StringSerializationStrategy<T> serializationStrategy;

    public AbstractWebsocketSink(String stream_uri) {
        super(stream_uri);
    }

    /**
     * Start the socket
     */
    public abstract void startSocket();

    public void removeConsumer(Consumer<T> c) {
        consumers.remove(c);

    }
    public StringSerializationStrategy<T> getSerializationStrategy(){
        return serializationStrategy;
    }
}
