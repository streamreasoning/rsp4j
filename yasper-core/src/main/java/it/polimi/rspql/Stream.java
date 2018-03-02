package it.polimi.rspql;

import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.StreamSchema;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDF stream?
public interface Stream {

    default StreamSchema getSchema() {
        return StreamSchema.UNKNOWN;
    }

    String getURI();

    void addWindowAssiger(WindowAssigner windowAssigner);
}
