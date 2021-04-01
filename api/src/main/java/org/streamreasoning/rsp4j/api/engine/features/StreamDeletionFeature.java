package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.stream.web.WebStream;

public interface StreamDeletionFeature<S extends WebStream> {

    void unregister(S s);

}
