package org.streamreasoning.rsp.distribution;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.util.Objects;

public class WebDataStreamSource<E> extends DataStreamImpl<E> implements SLD.WebDataStream<E> {

    private final SLD.Distribution d;
    private final SLD.Publisher p;
    private Graph description;

    public WebDataStreamSource(String stream_uri, Graph description, SLD.Distribution<E> d, SLD.Publisher p) {
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
    public SLD.Distribution[] distribution() {
        return new SLD.Distribution[]{d};
    }


    @Override
    public IRI uri() {
        return RDFUtils.createIRI(stream_uri);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WebDataStreamSource<?> that = (WebDataStreamSource<?>) o;
        return Objects.equals(d, that.d) && Objects.equals(p, that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), d, p, description);
    }
}
