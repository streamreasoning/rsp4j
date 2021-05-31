package org.streamreasoning.rsp;

import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

public interface WebStreamEndpoint<E> {

    WebDataStream<E> deploy();

    WebStream serve();


}
