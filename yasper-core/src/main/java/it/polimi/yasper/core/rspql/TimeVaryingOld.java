package it.polimi.yasper.core.rspql;

import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVaryingOld<I extends Item> {

    void setTimestamp(long t);

    I getContent(long now);

    void addObserver(Observer o);

}
