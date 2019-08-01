package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.web.WebStream;

public interface StreamDeletionFeature<S extends WebStream> {

    void unregister(S s);

}
