package org.streamreasoning.rsp.builders;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp.vocabulary.VSD;
import org.streamreasoning.rsp.vocabulary.XSD;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.streamreasoning.rsp.vocabulary.RDF.pTYPE;

public class WebStreamBuilder {

    private final SLD.Publisher p;
    String base, uri, name, description;
    List<DistributionBuilder> distributions = new ArrayList<>();
    Graph graph = RDFUtils.getInstance().createGraph();

    org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();


    public WebStreamBuilder(SLD.Publisher base) {
        this.p = base;
        this.base = p.uri().getIRIString();
        graph.add(is.createTriple(p.uri(), pTYPE, VSD.PUBLISHING_SERVICE));
    }

    public Graph describe() {
        return graph;
    }


    public WebStreamBuilder stream(String id, boolean fragment) {
        this.uri = (fragment) ? this.base + id : id;
        this.graph.add(is.createIRI(uri), pTYPE, VOCALS.STREAM_DESCRIPTOR);
        return this;
    }

    public WebStreamBuilder name(String name) {
        this.name = name;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pNAME, is.createLiteral(name, XSD.tString)));
        return this;
    }

    public WebStreamBuilder description(String description) {
        this.description = description;
        graph.add(is.createTriple(is.createIRI(base), DCAT.pDESCRIPTION, is.createLiteral(description, XSD.tString)));
        return this;
    }

    public WebStreamBuilder distribution(DistributionBuilder distribution) {
        distributions.add(distribution);
        distribution.describe().stream().forEach(t -> graph.add(t));
        return this;
    }

    public <T> SLD.Distribution<T>[] build() {
        return distributions.stream().map(distributionBuilder -> distributionBuilder.build(uri, true)).collect(Collectors.toList()).toArray(new SLD.Distribution[distributions.size()]);
    }

}