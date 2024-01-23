package org.streamreasoning.rsp4j.yasper.content;

import org.streamreasoning.rsp4j.api.secret.content.Content;

public class EmptyContent<I, O> implements Content<I, O> {

    long ts = System.currentTimeMillis();
    private O o;


    public EmptyContent(O o) {
        this.o = o;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void add(I e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return ts;
    }

    @Override
    public O coalesce() {
        return o;
    }
}
