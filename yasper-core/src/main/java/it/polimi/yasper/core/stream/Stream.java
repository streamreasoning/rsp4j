package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.stream.schema.StreamSchema;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface Stream {

    default StreamSchema getSchema() {
        return StreamSchema.UNKNOWN;
    }

    String getURI();

}
