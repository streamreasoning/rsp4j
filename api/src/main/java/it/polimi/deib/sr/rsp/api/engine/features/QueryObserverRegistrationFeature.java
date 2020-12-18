package it.polimi.deib.sr.rsp.api.engine.features;

import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResultFormatter o);

}
