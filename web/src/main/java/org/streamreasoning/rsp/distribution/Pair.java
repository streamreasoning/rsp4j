package org.streamreasoning.rsp.distribution;

public class Pair<E, T> {
    public final E e;
    public final T t;

    public Pair(E e, T t) {
        this.e = e;
        this.t = t;
    }

    @Override
    public String toString() {
        return "(" + e +
               "," + t +
               ')';
    }
}
