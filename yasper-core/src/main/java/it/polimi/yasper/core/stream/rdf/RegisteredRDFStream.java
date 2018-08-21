package it.polimi.yasper.core.stream.rdf;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.RegisteredStream;

import java.util.ArrayList;
import java.util.List;

public class RegisteredRDFStream<E> extends RDFStream implements RegisteredStream<E> {

    protected List<WindowAssigner<E>> assigners = new ArrayList<>();

    public RegisteredRDFStream(String stream_uri) {
        super(stream_uri);
    }

    @Override
    public String getURI() {
        return stream_uri;
    }

    @Override
    public void addWindowAssiger(WindowAssigner<E> windowAssigner) {
        assigners.add(windowAssigner);
    }

    @Override
    public void put(E e, long ts) {
        assigners.forEach(a -> a.notify(e, ts));
    }


}
