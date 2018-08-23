package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.utils.QueryConfiguration;

public interface QueryRegistrationFeature {

    ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c);

}
