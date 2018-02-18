package it.polimi.runtime;

import it.polimi.spe.content.ContentGraph;
import it.polimi.spe.content.viewer.View;
import org.apache.commons.rdf.api.Graph;

import java.util.Observable;
import java.util.Observer;

public class NamedStreamView implements View, Observer {

    @Override
    public void addObservable(Observable windowAssigner) {
        windowAssigner.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        ContentGraph content = (ContentGraph) arg;
        Graph graph = content.coalese();

    }
}
