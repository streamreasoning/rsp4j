package it.polimi.yasper.esper.rsp.internals.esper.internals.view.iterator;

import com.espertech.esper.collection.MixedEventBeanAndCollectionIteratorBase;
import com.espertech.esper.collection.TimeWindowPair;

import java.util.ArrayDeque;

public final class TimeWindowIterator extends MixedEventBeanAndCollectionIteratorBase {
    /**
     * Ctor.
     *
     * @param window is the time-slotted collection
     */
    public TimeWindowIterator(ArrayDeque<TimeWindowPair> window) {
        super(window.iterator());
        init();
    }

    protected Object getValue(Object iteratorKeyValue) {
        return ((TimeWindowPair) iteratorKeyValue).getEventHolder();
    }
}
