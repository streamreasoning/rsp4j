package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStream;

//TODO change the name as dereference
public interface StreamRegistrationFeature<S1 extends WebDataStream, S2 extends WebStream> {

    S1 register(S2 s);

}
