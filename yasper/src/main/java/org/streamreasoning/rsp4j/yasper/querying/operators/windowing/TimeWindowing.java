package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.WindowImpl;
import org.streamreasoning.rsp4j.api.secret.content.Content;

import java.util.Collections;
import java.util.Iterator;

public class TimeWindowing<I> implements Scope<I> {

    private Window currentOpenWindow;
    private final long startingTime, size, hop;


    public TimeWindowing(long startingTime, long size, long hop) {
        this.startingTime = startingTime;
        this.size = size;
        this.hop = hop;
    }

    @Override
    public Iterator<? extends Window> apply(I arg, long ts) {
        if(ts<startingTime)
            this.currentOpenWindow = new WindowImpl(startingTime,startingTime);

        long activeWinIndex = (long) Math.ceil(((double) Math.abs(ts - startingTime - size) / (double) hop));
        activeWinIndex = Math.max(0,activeWinIndex);

        long windowStartTime = startingTime + activeWinIndex * hop;

        return Collections.singletonList(new WindowImpl(windowStartTime,ts)).iterator();
    }

}
