package it.polimi.yasper.core.secret.content;


public interface Content<I,O> {
    int size();

    void add(I e);

    Long getTimeStampLastUpdate();

    //TODO CONSIDERING MAKING THIS INCONCISTENCY AWARE
    O coalesce();
}
