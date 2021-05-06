package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.WebDataStreamImpl;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWebsocketSink<T> extends WebDataStreamImpl<T> {


    protected StringSerializationStrategy<T> serializationStrategy;

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
