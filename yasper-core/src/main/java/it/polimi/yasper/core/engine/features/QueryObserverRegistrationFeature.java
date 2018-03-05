package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;

import java.lang.reflect.InvocationTargetException;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResponseFormatter o);

    void register(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryObserverRegistration";
    }
}
