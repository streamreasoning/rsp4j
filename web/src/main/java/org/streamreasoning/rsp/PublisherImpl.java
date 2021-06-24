package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.vocabulary.VSD;

import java.util.Objects;

public class PublisherImpl implements SLD.Publisher {
    private final IRI iri;
    private Graph graph;

    public PublisherImpl(IRI iri, Graph g) {
        this.iri = iri;
        this.graph = g;
        graph.add(VSD.publisher(this.uri()));
    }

    @Override
    public IRI uri() {
        return iri;
    }

    @Override
    public Graph describe() {
        return graph;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublisherImpl publisher = (PublisherImpl) o;
        return Objects.equals(iri, publisher.iri) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iri, graph);
    }
}
