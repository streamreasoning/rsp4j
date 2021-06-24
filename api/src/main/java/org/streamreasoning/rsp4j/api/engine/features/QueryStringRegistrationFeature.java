package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;

public interface QueryStringRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(String q);

    ContinuousQueryExecution register(String q, SDSConfiguration c);

}
