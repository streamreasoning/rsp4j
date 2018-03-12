package it.polimi.jasper.engine.querying.syntax;

import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import it.polimi.yasper.core.enums.WindowType;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface WindowOperatorNode extends WindowOperator{

    WindowType getType();

    int getT0();

    int getRange();

    int getStep();

    String getUnitRange();

    String getUnitStep();

}
