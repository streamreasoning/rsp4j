package org.streamreasoning.rsp.distribution;


import java.util.ArrayList;

public class LimitedList<E> extends ArrayList<Pair<E, Long>> {
    Integer maxSize;

    @Override
    public boolean add(Pair<E, Long> eLongPair) {
        boolean add = super.add(eLongPair);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize);
        }
        return add;
    }

    public LimitedList(Integer retention) {
        this.maxSize = retention;
    }


}
