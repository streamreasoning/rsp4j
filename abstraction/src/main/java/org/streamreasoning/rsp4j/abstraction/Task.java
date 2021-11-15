package org.streamreasoning.rsp4j.abstraction;

import org.streamreasoning.rsp4j.abstraction.containers.AggregationContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2RContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2SContainer;
import org.streamreasoning.rsp4j.abstraction.containers.S2RContainer;
import org.streamreasoning.rsp4j.api.sds.DataSet;

import java.util.List;
import java.util.Set;

public interface Task<I, W, R, O> {
    Set<S2RContainer<I, W>> getS2Rs();

    List<R2RContainer<W, R>> getR2Rs();

    Set<R2SContainer<R, O>> getR2Ss();

    List<AggregationContainer> getAggregations();

    DataSet<W> getDefaultGraph();
}
