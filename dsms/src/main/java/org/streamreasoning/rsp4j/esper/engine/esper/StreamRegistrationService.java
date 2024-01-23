package org.streamreasoning.rsp4j.esper.engine.esper;


import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.Map;

public interface StreamRegistrationService<T> {

    <T> DataStream register(DataStream s);

    <T> void unregister(DataStream s);

    Map<String, DataStream<T>> getRegisteredStreams();
}
