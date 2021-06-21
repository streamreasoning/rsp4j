package org.streamreasoning.rsp;

public interface WebStreamEndpoint<E> extends Describable {

    WebDataStream<E> serve();


}
