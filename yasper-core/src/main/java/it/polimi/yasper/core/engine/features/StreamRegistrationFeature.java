package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.Stream;

public interface StreamRegistrationFeature<S1 extends RegisteredStream, S2 extends Stream> {

    S1 register(S2 s);

}
