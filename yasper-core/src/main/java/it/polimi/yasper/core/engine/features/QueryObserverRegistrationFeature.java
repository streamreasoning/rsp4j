package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.spe.operators.r2s.result.QueryResultFormatter;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResultFormatter o);

}
