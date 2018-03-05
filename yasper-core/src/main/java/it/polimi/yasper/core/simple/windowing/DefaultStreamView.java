package it.polimi.yasper.core.simple.windowing;

import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssignerImpl;

import java.util.Observable;
import java.util.Observer;

public class DefaultStreamView extends Observable implements View, Observer {

    @Override
    public void observerOf(Object observer) {
        ((WindowAssignerImpl) observer).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }


}
