package it.polimi.sr.onsper.streams;

import it.polimi.rspql.Stream;
import it.polimi.yasper.core.stream.StreamSchema;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface RelStream extends Stream {

    @Override
    StreamSchema getSchema();
}
