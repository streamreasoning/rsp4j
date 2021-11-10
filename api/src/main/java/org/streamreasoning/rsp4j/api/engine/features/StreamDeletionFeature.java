package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;

public interface StreamDeletionFeature<T> {

    public void unregister(DataStream<T> s);
}
