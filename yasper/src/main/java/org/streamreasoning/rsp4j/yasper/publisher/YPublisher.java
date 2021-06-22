package org.streamreasoning.rsp4j.yasper.publisher;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.*;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp.vocabulary.VSD;
import org.streamreasoning.rsp.vocabulary.XSD;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.ArrayList;
import java.util.List;

import static org.streamreasoning.rsp.vocabulary.RDF.pTYPE;

public class YPublisher implements Publisher {

    String base, uri, name, description;
    List<Distribution> distributions = new ArrayList<>();
    Graph graph = RDFUtils.getInstance().createGraph();

    org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();


    public YPublisher(String base) {
        this.base = base;
        IRI s = is.createIRI(base);
        graph.add(is.createTriple(s, pTYPE, VSD.PUBLISHING_SERVICE));
    }

    @Override
    public Graph describe() {
        return graph;
    }


    @Override
    public Publisher stream(String id, boolean fragment) {
        this.uri = (fragment) ? this.base + id : id;
        this.graph.add(is.createIRI(uri), pTYPE, VOCALS.STREAM_DESCRIPTOR);
        return this;
    }

    @Override
    public Publisher name(String name) {
        this.name = name;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pNAME, is.createLiteral(name, XSD.tString)));
        return this;
    }

    @Override
    public Publisher description(String description) {
        this.description = description;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(description, XSD.tString)));
        return this;
    }

    @Override
    public Publisher distribution(Distribution distribution) {
        distribution.publisher(this);
        distributions.add(distribution);
        distribution.describe().stream().forEach(t -> graph.add(t));
        return this;
    }

    @Override
    public <E> WebStreamEndpoint<E> build() {
        return distributions.get(0).build(uri);
    }

    @Override
    public WebDataStream<String> fetch(String s) {
        //TODO read the rdf graph using jena/rdf4j
        //TODO parse the graph to extract distribution, instantiate a distribution object
        //TODO parse the graph to identify the parser
        return new WebDataStreamSource<>(s, null, null, null);
    }

}