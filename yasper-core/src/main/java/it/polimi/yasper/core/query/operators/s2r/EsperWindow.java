package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EventBean;
import it.polimi.rspql.Window;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by riccardo on 06/09/2017.
 */
public class EsperWindow implements Window<EventBean> {

    public EsperWindow(EventBean[] events) {
        this.events = events;
    }

    private EventBean[] events;

    @Override
    public int size() {
        return events.length;
    }

    @Override
    public boolean isEmpty() {
        return size() != 0;
    }

    @Override
    public boolean contains(Object o) {
        return Arrays.stream(events).anyMatch(e -> e.equals(o) || e.getUnderlying().equals(o));
    }

    @Override
    public Iterator iterator() {
        return Arrays.stream(events).iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(events).toArray();
    }

    @Override
    public boolean add(EventBean o) {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Window Are Immutable");
    }

    @Override
    public boolean containsAll(Collection c) {
        return c.stream().allMatch(e -> contains(e));
    }

    @Override
    public Object[] toArray(Object[] a) {
        //FIXME
        return a;
    }
}
