package it.polimi.rspql.timevarying;

import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.instantaneous.Instantaneous;

import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVaryingGraph {

    Instantaneous eval(long t);

    void setInstantaneousItem(Instantaneous i);

    void setWindowOperator(WindowOperator w);

    void addObserver(Observer o);
}
