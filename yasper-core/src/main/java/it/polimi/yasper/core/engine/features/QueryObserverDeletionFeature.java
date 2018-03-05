package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;

import java.lang.reflect.InvocationTargetException;

public interface QueryObserverDeletionFeature {

    void unregister(ContinuousQuery q, QueryResponseFormatter o);

    void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryObserverDeletion";
    }

}
