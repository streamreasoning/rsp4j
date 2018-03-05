package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.rspql.ContinuousQuery;

import java.lang.reflect.InvocationTargetException;

public interface QueryParsingFeature {

    ContinuousQuery parseQuery(String input);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryParsing";
    }
}
