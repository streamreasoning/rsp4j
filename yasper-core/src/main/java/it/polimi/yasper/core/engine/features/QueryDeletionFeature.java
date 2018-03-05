package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.quering.ContinuousQuery;

import java.lang.reflect.InvocationTargetException;

public interface QueryDeletionFeature {

    void unregister(ContinuousQuery qId);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:QueryDeletion";
    }

}
