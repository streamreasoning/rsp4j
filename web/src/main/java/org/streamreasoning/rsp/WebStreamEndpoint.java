package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

public interface WebStreamEndpoint<E> extends Describable {

    WebDataStream<E> deploy();

    WebStream serve();


}
