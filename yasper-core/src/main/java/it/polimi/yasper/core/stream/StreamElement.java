package it.polimi.yasper.core.stream;

public interface StreamElement<T> {

    long getTimestamp();

    T getContent();

}
