package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;

import java.lang.reflect.InvocationTargetException;

public interface QueryObserverRegistrationFeature {

    void register(ContinuousQuery q, QueryResponseFormatter o);

    void register(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryObserverRegistration";
    }
}
