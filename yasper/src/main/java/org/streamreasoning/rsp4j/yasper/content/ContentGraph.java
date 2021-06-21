package org.streamreasoning.rsp4j.yasper.content;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ContentGraph implements Content<Graph, Graph> {
    private Set<Graph> elements;
    private long last_timestamp_changed;

    public ContentGraph() {
        this.elements = new HashSet<>();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(Graph e) {
        elements.add(e);
        this.last_timestamp_changed = TimeFactory.getInstance().getAppTime();
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
            return elements.stream().findFirst().orElseGet(RDFUtils::createGraph);
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
