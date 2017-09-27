package it.polimi.jasper;

import it.polimi.jasper.engine.JenaRSPQLEngineImpl;
import it.polimi.yasper.core.utils.EngineConfiguration;
import it.polimi.yasper.core.utils.RSPQLEngineFactory;

/**
 * Created by riccardo on 01/09/2017.
 */
public class JenaRSPQLEngineImplFactory extends RSPQLEngineFactory {


    public static JenaRSPQLEngineImpl create(int i, EngineConfiguration ec) {
        JenaRSPQLEngineImpl e = new JenaRSPQLEngineImpl(i, ec);
        engine = e;
        return e;
    }
}
