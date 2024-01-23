package org.streamreasoning.rsp4j.csparql2.syntax;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.jena.graph.Node;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

/**
 * Created by Riccardo on 14/08/16.
 */
@AllArgsConstructor
public class StreamNode implements DataStream {
    @Getter
    @Setter

    private Node iri;

    @Override


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamNode stream = (StreamNode) o;

        return iri != null ? iri.equals(stream.iri) : stream.iri == null;
    }

    @Override
    public int hashCode() {
        return iri != null ? iri.hashCode() : 0;
    }

    @Override
    public void addConsumer(Consumer windowAssigner) {

    }

    @Override
    public void put(Object o, long ts) {

    }

    @Override
    public String getName() {
        return iri.getURI();
    }

    @Override
    public String toString() {
        return "StreamNode{" + "iri=" + iri + '}';
    }

}
