package it.polimi.yasper.core.engine.features;

import it.polimi.yasper.core.stream.Stream;

import java.lang.reflect.InvocationTargetException;

public interface StreamRegistrationFeature<S extends Stream> {

    S register(S s);

    default String toVocals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "vocals:StreamRegistration";
    }
}
