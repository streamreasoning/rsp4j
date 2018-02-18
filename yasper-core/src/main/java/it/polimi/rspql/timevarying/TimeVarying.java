package it.polimi.rspql.timevarying;

import it.polimi.rspql.Item;
import it.polimi.rspql.instantaneous.Instantaneous;
import it.polimi.spe.content.Content;
import it.polimi.spe.windowing.WindowOperator;
import it.polimi.spe.windowing.assigner.WindowAssigner;

import java.util.List;
import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVarying<I extends Item> {

    void setContent(I i);

    I getContent();

    <I extends Instantaneous> I eval(long t);

    void addObserver(Observer o);

    Content getContent(long now);

    List<Content> getContents(long now);
}
