package it.polimi.sr.rsp.onsper.spe.operators.r2r.obda;


import java.util.Collection;

public interface Relation<T> {

    Collection<T> getCollection();

    void add(T o);

    void remove(T o);

    void clear();

}
