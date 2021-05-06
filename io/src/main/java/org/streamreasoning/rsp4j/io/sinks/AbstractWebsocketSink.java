package org.streamreasoning.rsp4j.io.sinks;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWebsocketSink<T> implements WebDataStream<T> {

    protected List<Consumer<T>> consumers = new ArrayList<>();
    protected String stream_uri;
    protected StringSerializationStrategy<T> serializationStrategy;

    /**
     * Start the socket
     */
    public abstract void startSocket();

    @Override
    public void addConsumer(Consumer<T> c) {
        consumers.add(c);

    }
    public void removeConsumer(Consumer<T> c) {
        consumers.remove(c);

    }

    @Override
    public void put(T t, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(t, ts));

    }

    @Override
    public String uri() {
        return this.stream_uri;
    }
    public StringSerializationStrategy<T> getSerializationStrategy(){

        return serializationStrategy;
    }
}
