package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.stream.StreamElement;
import org.apache.commons.rdf.api.*;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;

public class ContentQuad implements Content {

    //They should all have the same graph
    private List<Quad> elements;
    private long last_timestamp_changed;
    private IRI graph_iri;
    private RDF rdf;

    public ContentQuad(IRI g, RDF rdf) {
        this.rdf = rdf;
        this.graph_iri = g;
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
        Triple content = (Triple) e.getContent();
        elements.add(rdf.createQuad(graph_iri, content.getSubject(), content.getPredicate(), content.getObject()));
        this.last_timestamp_changed = e.getTimestamp();
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return last_timestamp_changed;
    }


    @Override
    public String toString() {
        return elements.toString();
    }


    public Graph coalese() {
        RDF rdf = new SimpleRDF();
        Graph g = rdf.createGraph();
        elements.forEach(q -> g.add(q.asTriple()));
        return g;
    }
}
