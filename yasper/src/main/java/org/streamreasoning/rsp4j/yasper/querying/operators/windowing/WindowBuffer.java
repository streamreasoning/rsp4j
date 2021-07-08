package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.secret.content.Content;

import java.util.*;

public class WindowBuffer<I> implements Content<I, Collection<I>> {


    private SortedMap<Long,I> internalBuffer = new TreeMap<>();
    private final SortedMap<Long,I> activeWindow = new TreeMap<>();
    private final SortedSet<Window> windows;
    private long lastWinUpdateTime;

    public WindowBuffer(long lastWinUpdateTime) {
        this.lastWinUpdateTime = lastWinUpdateTime;
        this.windows = new TreeSet<>((o1, o2) -> (int) (o1.getC() - o2.getC()));
    }

    public void advance(Window w){
        internalBuffer = internalBuffer.tailMap(w.getO());
        lastWinUpdateTime = w.getC();
        windows.add(w);
    }

    @Override
    public int size() {
        return internalBuffer.size();
    }

    @Override
    public void add(I e) {
        internalBuffer.put(lastWinUpdateTime, e);
    }

    public void add(I e, long ts) {
        internalBuffer.put(ts, e);
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return lastWinUpdateTime;
    }

    @Override
    public Collection<I> coalesce() {
        return activeWindow.values();
    }
}
