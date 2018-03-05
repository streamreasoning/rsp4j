package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.rspql.ContinuousQuery;

import java.lang.reflect.InvocationTargetException;

public interface QueryDeletionFeature {

    void unregister(ContinuousQuery qId);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryDeletion";
    }

}
