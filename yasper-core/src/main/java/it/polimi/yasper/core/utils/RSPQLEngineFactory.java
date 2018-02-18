package it.polimi.yasper.core.utils;

import it.polimi.esper.RSPQLEngineImpl;

/**
 * Created by riccardo on 01/09/2017.
 */
public class RSPQLEngineFactory {

    protected static RSPQLEngineImpl engine;


    public static RSPQLEngineImpl getEngine(){
        return engine;
    }
}
