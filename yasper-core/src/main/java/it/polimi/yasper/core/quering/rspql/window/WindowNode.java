package it.polimi.yasper.core.quering.rspql.window;

import it.polimi.yasper.core.Named;
import it.polimi.yasper.core.enums.WindowType;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface WindowNode extends Named {

    WindowType getType();

    long getT0();

    long getRange();

    long getStep();

    String getUnitRange();

    String getUnitStep();

}
