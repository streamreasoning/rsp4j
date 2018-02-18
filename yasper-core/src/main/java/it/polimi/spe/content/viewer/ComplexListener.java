package it.polimi.spe.content.viewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ComplexListener implements View, Observer{

    static final Logger log = LoggerFactory.getLogger(ComplexListener.class);

    private List<View> parent_views = new ArrayList<>();

    public void addView(View v) {
        parent_views.add(v);
    }

    @Override
    public void update(Observable o, Object arg) {

        log.info("Content " + arg.toString());
    }

    @Override
    public void addObservable(Observable windowAssigner) {

    }
}
