package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.querying.QueryConfiguration;

public interface QueryStringRegistrationFeature<Q extends ContinuousQuery> {

    ContinuousQueryExecution register(String q);

    ContinuousQueryExecution register(String q, QueryConfiguration c);

}
