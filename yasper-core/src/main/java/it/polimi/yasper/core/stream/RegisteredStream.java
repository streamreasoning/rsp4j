package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface RegisteredStream<E> extends Stream {

    void addWindowAssiger(WindowAssigner<E> windowAssigner);

    void put(E e, long ts);

}
