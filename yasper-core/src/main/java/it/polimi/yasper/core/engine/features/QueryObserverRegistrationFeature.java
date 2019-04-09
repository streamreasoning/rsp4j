package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.format.QueryResultFormatter;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResultFormatter o);

}
