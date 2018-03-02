package it.polimi.yasper.core.rspql;

import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVarying<I extends Item> {

    <I extends Instantaneous> I eval(long t);

    I getContent(long now);

    void addObserver(Observer o);

}
