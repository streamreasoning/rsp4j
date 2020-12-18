package it.polimi.deib.sr.rsp.api.engine;

import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStream;
import it.polimi.deib.sr.rsp.api.stream.web.WebStreamImpl;

import java.util.Map;

public interface StreamRegistrationService<T> {

    <T> WebStreamImpl register(WebStream s);

    <T> void unregister(WebStream s);

    Map<String, WebDataStream<T>> getRegisteredStreams();
}
