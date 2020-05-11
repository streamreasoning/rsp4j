package it.polimi.jasper.engine.esper;

import it.polimi.jasper.streams.EPLStream;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStream;

import java.util.Map;

public interface StreamRegistrationService<T> {

    <T> DataStreamImpl register(WebStream s);

    <T> void unregister(WebStream s);

    Map<String, WebDataStream<T>> getRegisteredStreams();
}
