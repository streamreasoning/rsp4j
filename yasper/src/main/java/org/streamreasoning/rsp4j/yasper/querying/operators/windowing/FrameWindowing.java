package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.secret.content.Content;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;

public abstract class FrameWindowing<I,V> implements Scope<I> {


    protected Predicate<I> closePred, updatePred, openPred;
    protected CandidateWindow candidateWindow = new CandidateWindow(0L, 0L);
    private boolean init = true;

    //Final Windows map, with the end timestamp as the key
    protected SortedMap<Long, Window> windows;
    protected final FrameState<V> frameState = new FrameState<>();

    public FrameWindowing() {
        this.windows = new TreeMap<>();
    }

    @Override
    public Iterator<? extends Window> apply(I arg, long ts) {
        windows.headMap(ts).clear();
        if(closePred.test(arg))
            close(ts);
        if (updatePred.test(arg))
            update(ts, arg);
        if (openPred.test(arg))
            open(ts,arg);

        if(init)
            candidateWindow = new CandidateWindow(0L,ts);

        if(!windows.isEmpty())
            return windows.tailMap(ts).values().iterator();
        else return Collections.singletonList(candidateWindow).iterator();
    }

    public abstract void close(long ts);

    public abstract void open(long ts, I arg);

    public abstract void update(long ts, I arg);

}
