package it.polimi.yasper.core.spe.operators.s2r.syntax;

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
