package it.polimi.spe.content;


import it.polimi.spe.stream.StreamElement;

public interface Content {
    int size();

    void add(StreamElement e);

    Long getTimeStampLastUpdate();
}
