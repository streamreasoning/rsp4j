package org.streamreasoning.rsp4j.api.engine.features;

import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;

public interface QueryParsingFeature {

    ContinuousQuery parseQuery(String input);

}
