package it.polimi.yasper.core.runtime;

import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.rspql.Item;
import it.polimi.yasper.core.rspql.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssignerImpl;

import java.util.Observable;
import java.util.Observer;

public class DefaultStreamView extends Observable implements View, Observer, TimeVarying<Item> {


    @Override
    public void addObservable(Object windowAssigner) {
        ((WindowAssignerImpl) windowAssigner).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public Instantaneous eval(long t) {
        return null;
    }

    @Override
    public void addObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public Item getContent(long now) {
        return null;
    }

}
