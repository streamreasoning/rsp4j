package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;

//TODO change the name as dereference
public interface StreamRegistrationFeature<S1 extends WebDataStream, S2 extends WebStream> {

    S1 register(S2 s);

}
