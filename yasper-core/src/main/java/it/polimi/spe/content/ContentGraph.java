package it.polimi.spe.content;

import it.polimi.spe.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        elements.stream()
                .flatMap(graph -> graph.stream())
                .forEach(t -> g.add(t));

        return g;
    }
}
