package it.polimi.rspql;

import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.stream.StreamSchema;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDF stream?
public interface Stream {

    default StreamSchema getSchema() {
        return StreamSchema.UNKNOWN;
    }

    String getTboxUri();

    String getURI();



}
