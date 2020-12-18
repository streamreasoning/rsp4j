package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;

public interface QueryParsingFeature {

    ContinuousQuery parseQuery(String input);

}
