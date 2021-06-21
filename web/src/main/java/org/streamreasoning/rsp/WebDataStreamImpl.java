package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

public class WebDataStreamImpl<E> extends DataStreamImpl<E> implements WebDataStream<E> {

    private Graph description;

    public WebDataStreamImpl(String stream_uri, Graph description) {
        super(stream_uri);
        this.description = description;
    }

    @Override
    public Graph describe() {
        return description;
    }
}
