package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;

public interface QueryRegistrationFeature<Q extends ContinuousQuery> {

    <I, W, R, O> ContinuousQueryExecution<I, W, R, O> register(Q q);

}
