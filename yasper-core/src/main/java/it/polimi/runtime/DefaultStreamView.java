package it.polimi.runtime;

import it.polimi.spe.content.viewer.View;

import java.util.Observable;
import java.util.Observer;

public class DefaultStreamView implements View, Observer {


    @Override
    public void addObservable(Observable windowAssigner) {
        windowAssigner.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
