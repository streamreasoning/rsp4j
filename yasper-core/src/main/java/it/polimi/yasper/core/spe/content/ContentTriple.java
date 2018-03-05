package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;

public class ContentTriple implements Content {

    private List<Triple> elements;
    private long last_timestamp_changed;

    public ContentTriple() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
        elements.add((Triple) e.getContent());
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

    @Override
    public Graph coalese() {
        RDF rdf = new SimpleRDF();
        Graph g = rdf.createGraph();
        elements.forEach(g::add);
        return g;
    }
}
