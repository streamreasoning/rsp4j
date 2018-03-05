package it.polimi.yasper.core.reasoning;

import it.polimi.yasper.core.stream.Instantaneous;

/**
 * Created by riccardo on 06/07/2017.
 */
public interface TVGReasoner<F extends Instantaneous, I extends Instantaneous> {

    F bind(I data);

    TVGReasoner<F, I> bindSchema(I g);

}
