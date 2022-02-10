package org.streamreasoning.rsp4j.operatorapi;

import org.streamreasoning.rsp4j.operatorapi.containers.AggregationContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.R2RContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.R2SContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.S2RContainer;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;

import java.util.List;
import java.util.Set;

public interface Task<I, W, R, O> {
    Set<S2RContainer<I, W>> getS2Rs();

    List<R2RContainer<W, R>> getR2Rs();

    Set<R2SContainer<R, O>> getR2Ss();

    List<AggregationContainer> getAggregations();

    DataSet<W> getDefaultGraph();

    List<Var> getProjection();
}
