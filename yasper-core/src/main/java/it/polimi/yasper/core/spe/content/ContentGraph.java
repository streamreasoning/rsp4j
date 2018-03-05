package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.spe.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentGraph implements Content {
    private static final RDF rdf = new SimpleRDF();
    private List<Graph> elements;
    private long last_timestamp_changed;

    public ContentGraph() {
        this.elements = new ArrayList<>();
    }

    public ContentGraph(Graph g) {
        this.elements = Collections.singletonList(g);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(StreamElement e) {
        Object content = e.getContent();
        Graph graph;
        if (content instanceof Triple) {
            graph = rdf.createGraph();
            graph.add((Triple) content);

        } else {
            graph = (Graph) content;
        }

        elements.add(graph);
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
        if (elements.size() == 1)
            return elements.get(0);
        else {
            Graph g = rdf.createGraph();
            elements.stream().flatMap(Graph::stream).forEach(g::add);
            return g;
        }
    }

    public ContentGraph coaleseContent() {
        return new ContentGraph(coalese());
    }
}
