package it.polimi.rspql;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface Visitable {
    void accept(SDSBuilder v);
}
