package org.streamreasoning.rsp4j.yasper.stream;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

import java.util.ArrayList;
import java.util.List;

public class WebDataStreamWrapper<I> implements WebDataStream<I> {


    private final WebStream stream;
    protected String stream_uri;


    protected List<Consumer<I>> consumers = new ArrayList<>();

    private WebDataStreamWrapper(WebStream stream){
        this.stream = stream;
        this.stream_uri = stream.uri();
    }
    public static WebDataStreamWrapper from(WebStream stream){
        WebDataStreamWrapper wrappedStream = new WebDataStreamWrapper(stream);
        return wrappedStream;
    }
    @Override
    public void addConsumer(Consumer<I> c) {
        consumers.add(c);
    }

    @Override
    public void put(I e, long ts) {
        consumers.forEach(c -> c.notify(e, ts));
    }

    @Override
    public String uri() {
        return stream_uri;
    }
}
