package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

public class WebDataStreamSink<E> extends DataStreamImpl<E> implements WebDataStream<E> {

    private final Distribution d;
    private final Publisher p;
    private Graph description;

    public WebDataStreamSink(String stream_uri, Graph description, Distribution d, Publisher p) {
        super(stream_uri);
        this.description = description;
        this.d=d;
        this.p=p;
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
}
