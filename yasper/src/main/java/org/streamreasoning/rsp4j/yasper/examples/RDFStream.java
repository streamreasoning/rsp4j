package org.streamreasoning.rsp4j.yasper.examples;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.ArrayList;
import java.util.List;

public class RDFStream implements DataStream<Graph> {

    protected String stream_uri;
    protected List<Consumer<Graph>> consumers = new ArrayList<>();

    public RDFStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    @Override
    public void addConsumer(Consumer<Graph> c) {
        consumers.add(c);
    }

    @Override
    public void put(Graph e, long ts) {
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
