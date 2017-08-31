package it.polimi.rspql.timevarying;

import it.polimi.rspql.Item;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.instantaneous.Instantaneous;

import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVarying<I extends Item> {

    void setContent(I i);

    I getContent();

    <I extends Instantaneous> I eval(long t);

    void setWindowOperator(WindowOperator w);

    void addObserver(Observer o);
}
