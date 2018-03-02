package it.polimi.yasper.core.rspql;

import it.polimi.yasper.core.spe.content.Content;

import java.util.List;
import java.util.Observer;

/**
 * Created by riccardo on 02/09/2017.
 */
public interface TimeVarying<I extends Item> {

    I getContent();

    <I extends Instantaneous> I eval(long t);

    void addObserver(Observer o);

    Content getContent(long now);

    List<Content> getContents(long now);
}
