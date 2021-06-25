package org.streamreasoning.rsp.distribution;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;

import java.util.Objects;

@Log4j
public abstract class AbstractDistribution<E> implements SLD.Distribution<E> {

    protected final BlankNodeOrIRI uri;
    protected final String access;
    protected final License license;
    protected final Format format;
    protected final SLD.Publisher p;
    protected final boolean source;
    protected SLD.WebDataStream<E> dataStream;
    protected Graph graph;

    public AbstractDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = false;
        this.dataStream = new WebDataStreamSink<E>(access, this.graph, this, p);
        this.graph = graph;
    }

    public AbstractDistribution(BlankNodeOrIRI uri, String access, License license, Format format, SLD.Publisher p, Graph graph, boolean source) {
        this.uri = uri;
        this.access = access;
        this.license = license;
        this.format = format;
        this.p = p;
        this.source = source;
        this.graph = graph;
        this.dataStream = new WebDataStreamSource<>(access, describe(), this, p);
    }


    @Override
    public SLD.WebDataStream<E> getWebStream() {
        if (dataStream == null) {
            serve();
        }
        return dataStream;
    }


    @Override
    public Graph describe() {
        return graph;
    }

    @Override
    public IRI uri() {
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDistribution<?> that = (AbstractDistribution<?>) o;
        return source == that.source && Objects.equals(access, that.access) && license == that.license && format == that.format;
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, license, format, source);
    }
}

