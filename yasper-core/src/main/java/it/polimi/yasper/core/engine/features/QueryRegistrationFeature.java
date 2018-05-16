package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.utils.QueryConfiguration;

public interface QueryRegistrationFeature {

    ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c);

}
