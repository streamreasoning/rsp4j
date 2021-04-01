package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.format.QueryResultFormatter;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResultFormatter o);

}
