package it.polimi.sr.onsper.query.instantaneous;

import it.polimi.yasper.core.query.Updatable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by riccardo on 05/09/2017.
 */
@RequiredArgsConstructor
public class IntantaneousRelationImpl implements InstantaneousRelation, Updatable<Object>{

    @NonNull
    private Collection<Object> content;
    @NonNull
    private long timestamp;

    public IntantaneousRelationImpl() {
        this.content = new ArrayList<>();
        this.timestamp = System.currentTimeMillis();
    }


    @Override
    public void add(Object o) {
        content.add(o);
    }

    @Override
    public void remove(Object o) {
        content.remove(o);
    }

    @Override
    public boolean contains(Object o) {
        return content.contains(o);
    }

    @Override
    public boolean isSetSemantics() {
        return Set.class.equals(content.getClass());
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long ts) {
        timestamp = ts;
    }

    @Override
    public void clear() {

    }
}
