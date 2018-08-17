package it.polimi.yasper.core.stream.rdf;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamElement;

import java.util.ArrayList;
import java.util.List;

public class RegisteredRDFStream extends RDFStream implements RegisteredStream {

    List<WindowAssigner> assigners = new ArrayList<>();

    public RegisteredRDFStream(String stream_uri) {
        super(stream_uri);
    }

    @Override
    public String getURI() {
        return stream_uri;
    }

    @Override
    public void addWindowAssiger(WindowAssigner windowAssigner) {
        assigners.add(windowAssigner);
    }

    @Override
    public void put(StreamElement e) {
        assigners.forEach(a -> a.notify(e));
    }


}
