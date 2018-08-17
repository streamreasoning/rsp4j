package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.Stream;

public interface StreamDeletionFeature<S extends Stream> {

    void unregister(S s);

}
