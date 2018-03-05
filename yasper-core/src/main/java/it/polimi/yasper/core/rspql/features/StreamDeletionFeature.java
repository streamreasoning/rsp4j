package it.polimi.yasper.core.rspql.features;

import it.polimi.yasper.core.rspql.Stream;

import java.lang.reflect.InvocationTargetException;

public interface StreamDeletionFeature<S extends Stream> {

    void unregister(S s);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:StreamDeletion";
    }
}
