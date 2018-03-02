package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.spe.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;

public class ContentGraph implements Content {

    private List<Graph> elements;
    private long last_timestamp_changed;

    public ContentGraph() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
        elements.add((Graph) e.getContent());
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
        elements.stream().flatMap(Graph::stream).forEach(g::add);

        return g;
    }
}
