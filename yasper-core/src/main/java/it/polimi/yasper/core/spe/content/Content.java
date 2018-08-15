package it.polimi.yasper.core.spe.content;


import it.polimi.yasper.core.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;

public interface Content<T> {
    int size();

    void add(StreamElement e);

    Long getTimeStampLastUpdate();

    T coalesce();
}
