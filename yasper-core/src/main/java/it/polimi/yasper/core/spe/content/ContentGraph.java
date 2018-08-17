package it.polimi.yasper.core.spe.content;

import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.utils.RDFUtils;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ContentGraph implements Content<Graph> {
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
        Object content = e.getContent();
        Graph graph;
        if (content instanceof Triple) {
            graph = RDFUtils.createGraph();
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
    public Graph coalesce() {
        if (elements.size() == 1)
            return elements.get(0);
        else {
            Graph g = RDFUtils.createGraph();
            elements.stream().flatMap(Graph::stream).forEach(g::add);
            return g;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentGraph that = (ContentGraph) o;
        return last_timestamp_changed == that.last_timestamp_changed &&
                Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements, last_timestamp_changed);
    }
}
