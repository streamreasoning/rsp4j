package org.streamreasoning.rsp4j.abstraction.table;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.ArrayList;
import java.util.List;

public class BindingStream implements DataStream<Binding> {
    protected String stream_uri;
    protected List<Consumer<Binding>> consumers = new ArrayList<>();


    public BindingStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }


    @Override
    public void addConsumer(Consumer<Binding> c) {
        consumers.add(c);
    }

    @Override
    public void put(Binding e, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(e, ts));
    }

    @Override
    public String getName() {
        return stream_uri;
    }

    public String uri() {
        return stream_uri;
    }
}
