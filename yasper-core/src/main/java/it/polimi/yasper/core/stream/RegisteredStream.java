package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;

/**
 * Created by riccardo on 10/07/2017.
 */

//TODO wrap schema for RDFUtils stream?
public interface RegisteredStream extends Stream {

    void addWindowAssiger(WindowAssigner windowAssigner);
}
