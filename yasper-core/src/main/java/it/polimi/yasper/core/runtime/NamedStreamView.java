package it.polimi.yasper.core.runtime;

import it.polimi.yasper.core.rspql.Item;
import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.rspql.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * This class de-facto implement a view over a stream.
 * While the Window Assigner represents the TimeVarying graph as function
 * this class represents the application of a TVG at t.
 *
 * It is the class resposible to maintain a querable form.
 *
 * Do we need this to be asyncrhnous?
 *
 * In theory we could just notify to the extencution that we had an update in the ET.
 *
 * **/
public class NamedStreamView extends Observable implements View, Observer, TimeVarying {

    TimeVarying tvg;
    private Object content;

    @Override
    public void addObservable(Observable windowAssigner) {
        windowAssigner.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        //     ContentTriple content = (ContentTriple) arg;
//        Graph graph = content.coalese();
        this.content = arg;

        setChanged();
        notifyObservers();

        //IDEA: the window assigner alerts the view that the content is visible passing to it the timestamp
        //representing the key to access. It has a reference to a TVG (which is also the Window Assigner
        // calling the eval method to the TVG and passing the key (a timestamp) it results to the content to update
        // then it can notify the continuous execution again passing the timestamp
        //the continuous execution has a reference to SDS and can evaluate it
    }

    @Override
    public Item getContent() {
        return null;
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public Content getContent(long now) {
        return null;
    }

    @Override
    public List<Content> getContents(long now) {
        return null;
    }

    @Override
    public Instantaneous eval(long t) {
        return null;
    }
}
