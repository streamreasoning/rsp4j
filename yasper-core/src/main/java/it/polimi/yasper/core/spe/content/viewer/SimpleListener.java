package it.polimi.yasper.core.spe.content.viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;
import java.util.Observer;

public class SimpleListener implements View, Observer {


    static final Logger log = LoggerFactory.getLogger(SimpleListener.class);

    @Override
    public void update(Observable o, Object arg) {
        log.info("Content " + arg.toString());
    }

    @Override
    public void addObservable(Observable windowAssigner) {

    }
}
