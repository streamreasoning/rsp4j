package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;

import java.lang.reflect.InvocationTargetException;

public interface QueryObserverDeletionFeature {

    void unregister(ContinuousQuery q, QueryResponseFormatter o);

    void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryObserverDeletion";
    }

}
