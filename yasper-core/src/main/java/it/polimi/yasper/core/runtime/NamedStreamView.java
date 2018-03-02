package it.polimi.yasper.core.runtime;

import it.polimi.yasper.core.rspql.Instantaneous;
import it.polimi.yasper.core.rspql.Item;
import it.polimi.yasper.core.rspql.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.ContentGraph;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.content.EmptyContent;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssignerImpl;
import lombok.Getter;

import java.util.Observable;
import java.util.Observer;

/**
 * This class de-facto implements a view over a windowed stream.
 * While the Window Assigner represents the TimeVarying graph as function
 * this class represents the application of a TVG at t.
 * <p>
 * It is the class resposible to maintain a querable form.
 * <p>
 * Do we need this to be asyncrhnous?
 * <p>
 * In theory we could just notify to the extencution that we had an update in the ET.
 **/
@Getter
public class NamedStreamView extends Observable implements View, Observer, TimeVarying {

    private final WindowAssigner tvg;
    private Content content = new EmptyContent();

    public NamedStreamView(WindowAssigner tvg) {
        this.tvg = tvg;

    }

    @Override
    public void addObservable(Object windowAssigner) {
        ((WindowAssignerImpl) windowAssigner).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {

        //     ContentTriple content = (ContentTriple) arg;
//        GraphItem graph = content.coalese();

        Long ts = (Long) arg;

        this.content = tvg.getContent(ts);

        if (content instanceof ContentGraph)
            content = ((ContentGraph) content).coalese();

        setChanged();
        notifyObservers(ts);

        //IDEA: the window assigner alerts the view that the content is visible passing to it the timestamp
        // representing the key to access. It has a reference to a TVG (which is also the Window Assigner
        // calling the eval method to the TVG and passing the key (a timestamp) it results to the content to update
        // then it can notify to the continuous execution again passing the timestamp
        // the continuous execution has a reference to SDS and can evaluate it
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public Item getContent(long now) {
        return null;
    }

    @Override
    public Instantaneous eval(long t) {
        return null;
    }
}
