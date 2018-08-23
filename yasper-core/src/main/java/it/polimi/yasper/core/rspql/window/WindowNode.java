package it.polimi.yasper.core.rspql.window;

import it.polimi.yasper.core.spe.WindowType;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface WindowNode {

    String iri();

    boolean named();

    WindowType getType();

    long getT0();

    long getRange();

    long getStep();

    String getUnitRange();

    String getUnitStep();

}
