package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.spe.operators.r2r.QueryConfiguration;

public interface QueryStringRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(String q);

    ContinuousQueryExecution register(String q, QueryConfiguration c);

}
