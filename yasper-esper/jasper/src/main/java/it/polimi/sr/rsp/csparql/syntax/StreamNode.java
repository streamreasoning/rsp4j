package it.polimi.sr.rsp.csparql.syntax;

import it.polimi.yasper.core.stream.web.WebStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.jena.graph.Node;

/**
 * Created by Riccardo on 14/08/16.
 */
@AllArgsConstructor
public class StreamNode implements WebStream {
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
    public String getURI() {
        return iri.getURI();
    }

    @Override
    public String toString() {
        return "StreamNode{" + "iri=" + iri + '}';
    }

}
