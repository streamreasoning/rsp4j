package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

public class WebDataStreamSink<E> extends DataStreamImpl<E> implements SLD.WebDataStream<E> {

    private final SLD.Distribution<E> d;
    private final SLD.Publisher p;
    private Graph description;

    public WebDataStreamSink(String stream_uri, Graph description, SLD.Distribution<E> d, SLD.Publisher p) {
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
    public SLD.Publisher publisher() {
        return p;
    }

    @Override
    public SLD.Distribution<E>[] distribution() {
        return new SLD.Distribution[]{d};
    }

    @Override
    public IRI uri() {
        return RDFUtils.createIRI(stream_uri);
    }
}
