package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.formatter.QueryResponseFormatter;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResponseFormatter o);

}
