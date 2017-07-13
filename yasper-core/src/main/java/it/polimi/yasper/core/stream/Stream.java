package it.polimi.yasper.core.stream;

import it.polimi.yasper.core.engine.RSPEngine;

/**
 * Created by riccardo on 10/07/2017.
 */
public interface Stream {

    void setRSPEngine(RSPEngine e);
    RSPEngine getRSPEngine();

    String getURI();

    String toEPLSchema();
}
