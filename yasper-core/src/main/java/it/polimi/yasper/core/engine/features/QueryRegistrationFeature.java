package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.spe.operators.r2r.QueryConfiguration;

public interface QueryRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(Q q);

    ContinuousQueryExecution register(Q q, QueryConfiguration c);

}
