package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.utils.QueryConfiguration;

import java.lang.reflect.InvocationTargetException;

public interface QueryRegistrationFeature {

    ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c);

    ContinuousQueryExecution register(String q, QueryConfiguration c);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryRegistration";
    }


}
