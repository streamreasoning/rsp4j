package it.polimi.yasper.core.spe.content;


import it.polimi.yasper.core.spe.stream.StreamElement;
import org.apache.commons.rdf.api.Graph;

public interface Content {
    int size();

    void add(StreamElement e);

    Long getTimeStampLastUpdate();

    Graph coalese();
}
