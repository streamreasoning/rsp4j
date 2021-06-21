package org.streamreasoning.rsp4j.api.stream.data;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.Consumer;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface DataStream<E> {

    void addConsumer(Consumer<E> windowAssigner);

    void put(E e, long ts);

}
