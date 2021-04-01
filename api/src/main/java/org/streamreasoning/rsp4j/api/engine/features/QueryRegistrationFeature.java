package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;

public interface QueryRegistrationFeature<Q extends ContinuousQuery> {

    <I, C, O> ContinuousQueryExecution<I, C, O> register(Q q);

}
