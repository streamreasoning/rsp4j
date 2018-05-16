package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.Stream;

public interface StreamRegistrationFeature<S extends Stream> {

    S register(S s);

}
