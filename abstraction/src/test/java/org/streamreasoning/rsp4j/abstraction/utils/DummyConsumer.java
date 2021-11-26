package org.streamreasoning.rsp4j.abstraction.utils;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;

import java.util.ArrayList;
import java.util.List;

public class DummyConsumer<O> implements Consumer<O> {
    private List<O> received = new ArrayList<O>();

    @Override
    public void notify(O event, long ts) {
        received.add(event);
    }

    public int getSize() {
        return received.size();
    }

    public List<O> getReceived() {
        return received;
    }
}
