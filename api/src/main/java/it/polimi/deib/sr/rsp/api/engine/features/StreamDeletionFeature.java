package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.stream.web.WebStream;

public interface StreamDeletionFeature<S extends WebStream> {

    void unregister(S s);

}
