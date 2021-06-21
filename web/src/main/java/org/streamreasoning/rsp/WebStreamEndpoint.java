package org.streamreasoning.rsp;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

public interface WebStreamEndpoint<E> extends Describable {

    DataStream<E> deploy();

    WebStream serve();


}