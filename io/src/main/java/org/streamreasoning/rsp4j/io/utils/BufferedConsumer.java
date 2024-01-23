package org.streamreasoning.rsp4j.io.utils;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;

import java.util.ArrayList;
import java.util.List;

public class BufferedConsumer<T> implements Consumer<T> {

    public List<T> buffer;

    public BufferedConsumer() {
        this.buffer = new ArrayList<T>();
    }

    @Override
    public void notify(T arg, long ts) {
        this.buffer.add(arg);
    }

    public int getSize() {
        return this.buffer.size();
    }

    public List<T> getMessages() {
        return buffer;
    }

    public T getMessage(int index) {
        if (index < buffer.size()) {
            return buffer.get(index);
        } else {
            return null;
        }
    }
}
