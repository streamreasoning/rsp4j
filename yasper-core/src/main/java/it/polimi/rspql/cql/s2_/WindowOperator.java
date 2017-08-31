package it.polimi.rspql.cql.s2_;

import it.polimi.rspql.Stream;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.rspql.Window;
import it.polimi.yasper.core.enums.WindowType;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface WindowOperator<V extends TimeVarying, S extends Stream> extends StreamTo_Operator {

    WindowType getType();

    String getName();

    boolean isNamed();

    int getT0();

    int getRange();

    int getStep();

    String getUnitRange();

    String getUnitStep();

    Window getWindowContent(long t0);

    V apply(S s);

}
