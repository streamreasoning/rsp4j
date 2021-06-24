package org.streamreasoning.rsp.builders;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp.SLD;
import org.streamreasoning.rsp.vocabulary.DCAT;
import org.streamreasoning.rsp.vocabulary.VOCALS;
import org.streamreasoning.rsp4j.api.RDFUtils;

import java.util.ArrayList;
import java.util.List;

public class WebStreamBuilder {

    private SLD.Publisher p;

    public String base() {
        return base;
    }

    private String base;
    private IRI uri;
    private String name;
    private String description;
    private List<DistributionBuilder> distributionBuilders = new ArrayList<>();
    private Graph graph = RDFUtils.getInstance().createGraph();

    org.apache.commons.rdf.api.RDF is = RDFUtils.getInstance();
    private String id;
    private boolean fragment;


    public WebStreamBuilder(String p) {
        this.base = p;

    }

    public WebStreamBuilder publisher(SLD.Publisher p) {
        this.p = p;
        p.describe().stream().forEach(graph::add);
        return this;
    }

    public WebStreamBuilder stream(String id, boolean fragment) {
        this.fragment = fragment;
        this.uri = is.createIRI((fragment) ? this.base + "/" + id : id);
        this.id = id;
        Triple descriptor = VOCALS.descriptor();
        this.graph.add(descriptor);
        this.graph.add(VOCALS.stream(uri));
        this.graph.add(DCAT.dataset(descriptor.getSubject(), uri));
        return this;
    }

    public WebStreamBuilder name(String name) {
        this.name = name;
        graph.add(DCAT.name(uri, name));
        return this;
    }

    public WebStreamBuilder description(String description) {
        this.description = description;
        graph.add(DCAT.description(uri, name));
        return this;
    }

    public WebStreamBuilder distribution(DistributionBuilder db) {
        db.publisher(p);
        distributionBuilders.add(db);
        db.describe().stream().forEach(t -> graph.add(t));
        return this;
    }

    public <T> SLD.WebStream<T> build() {
        return new SLD.WebStream<T>() {
            @Override
            public SLD.Publisher publisher() {
                return p;
            }

            @Override
            public SLD.WebDataStream<T> serve() {
                DistributionBuilder distributionBuilder = distributionBuilders.get(0);
                return distributionBuilder.<T>buildSink(graph).serve();
            }

            @Override
            public Graph describe() {
                return graph;
            }

            @Override
            public IRI uri() {
                return uri;
            }
        };
    }


    public DistributionBuilder newDistribution() {
        return new DistributionBuilder(base);
    }
}