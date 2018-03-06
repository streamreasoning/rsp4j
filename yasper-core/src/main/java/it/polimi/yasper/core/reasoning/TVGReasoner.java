package it.polimi.yasper.core.reasoning;

/**
 * Created by riccardo on 06/07/2017.
 */
public interface TVGReasoner<F,I> {

    F bindTVG(I data);

    TVGReasoner<F, I> bindSchemaTVG(I g);

}
