package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;

//TODO change the name as dereference
public interface StreamRegistrationFeature<S1 extends DataStream, S2 extends DataStream> {

    S1 register(S2 s);

}
