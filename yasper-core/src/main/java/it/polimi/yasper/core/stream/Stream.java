package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.stream.schema.StreamSchema;

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
