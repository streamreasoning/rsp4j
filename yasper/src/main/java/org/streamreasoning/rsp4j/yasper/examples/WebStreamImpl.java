package org.streamreasoning.rsp4j.yasper.examples;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;

import java.util.ArrayList;
import java.util.List;

public class WebStreamImpl<T> implements WebDataStream<T> {

    protected String stream_uri;

    public WebStreamImpl(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    protected List<Consumer<T>> consumers = new ArrayList<>();

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
