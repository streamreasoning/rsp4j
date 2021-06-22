package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

public class WebDataStreamSource<E> extends towa<E> implements WebDataStream<E> {

    private final Distribution d;
    private final Publisher p;
    private Graph description;

    public WebDataStreamSource(String stream_uri, Graph description, Distribution d, Publisher p) {
        super(stream_uri);
        this.description = description;
        this.d = d;
        this.p = p;
    }

    @Override
    public Graph describe() {
        return description;
    }

    @Override
    public Publisher publisher() {
        return p;
    }

    @Override
    public Distribution distribution() {
        return d;
    }

    @Override
    public void put(E e, long ts) {
        throw new UnsupportedOperationException("You don't have the rights to write on such stream");
    }
}
