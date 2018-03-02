package it.polimi.yasper.core.rspql;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface Updatable<T extends Object> {

    void add(T o);

    void remove(T o);

    boolean contains(T o);

    boolean isSetSemantics();

    void clear();
}
