package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStream;

//TODO change the name as dereference
public interface StreamRegistrationFeature<S1 extends WebDataStream, S2 extends WebStream> {

    S1 register(S2 s);

}
