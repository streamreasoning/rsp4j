package it.polimi.yasper.core.stream.data;

import it.polimi.yasper.core.operators.s2r.execution.assigner.Consumer;
import it.polimi.yasper.core.stream.web.WebStreamImpl;

import java.util.ArrayList;
import java.util.List;

public class DataStreamImpl<E> extends WebStreamImpl implements WebDataStream<E> {

    protected List<Consumer<E>> consumers = new ArrayList<>();

    public DataStreamImpl(String stream_uri) {
        super(stream_uri);
    }

    @Override
    public String getURI() {
        return stream_uri;
    }


    @Override
    public void addConsumer(Consumer<E> windowAssigner) {
        consumers.add(windowAssigner);
    }

    @Override
    public void put(E e, long ts) {
            consumers.forEach(a -> a.notify(e, ts));
    }


}
