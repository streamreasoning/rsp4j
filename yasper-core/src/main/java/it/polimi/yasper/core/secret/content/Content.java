package it.polimi.yasper.core.secret.content;


public interface Content<T> {
    int size();

    void add(T e);

    Long getTimeStampLastUpdate();

    //TODO CONSIDERING MAKING THIS INCONCISTENCY AWARE
    T coalesce();
}
