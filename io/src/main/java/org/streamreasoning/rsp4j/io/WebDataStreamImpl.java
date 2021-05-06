package org.streamreasoning.rsp4j.io;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for {@link WebDataStream} providing functionality for interaction with consumers.
 *
 * @param <T>  The generic type of objects in the stream.
 */
public class WebDataStreamImpl<T> implements WebDataStream<T> {
    protected List<Consumer<T>> consumers = new ArrayList<>();
    protected String stream_uri;
    @Override
    public void addConsumer(Consumer<T> c) {
        consumers.add(c);

    }


    @Override
    public void put(T t, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(t, ts));

    }

    @Override
    public String uri() {
        return this.stream_uri;
    }
}
