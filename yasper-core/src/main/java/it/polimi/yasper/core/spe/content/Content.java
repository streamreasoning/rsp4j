package it.polimi.yasper.core.spe.content;


import it.polimi.yasper.core.spe.stream.StreamElement;

public interface Content {
    int size();

    void add(StreamElement e);

    Long getTimeStampLastUpdate();
}
