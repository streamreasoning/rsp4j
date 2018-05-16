package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;

public interface QueryExecutionObserverRegistrationFeature {

    void register(ContinuousQueryExecution cqe, QueryResponseFormatter o);

}
