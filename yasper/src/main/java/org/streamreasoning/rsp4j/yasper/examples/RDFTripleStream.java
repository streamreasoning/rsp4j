package org.streamreasoning.rsp4j.yasper.examples;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Triple;

import java.util.ArrayList;
import java.util.List;

public class RDFTripleStream implements WebDataStream<Triple> {

    protected String stream_uri;

    public RDFTripleStream(String stream_uri) {
        this.stream_uri = stream_uri;
    }

    protected List<Consumer<Triple>> consumers = new ArrayList<>();

    @Override
    public void addConsumer(Consumer<Triple> c) {
        consumers.add(c);
    }

    @Override
    public void put(Triple e, long ts) {
        consumers.forEach(graphConsumer -> graphConsumer.notify(e, ts));
    }

    @Override
    public String uri() {
        return stream_uri;
    }

}
