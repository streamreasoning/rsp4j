package it.polimi.yasper.core.simple.windowing;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.EmptyContent;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssignerImpl;
import lombok.Getter;

import java.util.Observable;
import java.util.Observer;

/**
 * This class de-facto implements a view over a windowed stream.
 * While the Window Assigner represents the TimeVaryingOld graph as function
 * this class represents the application of a TVG at t.
 * <p>
 * It is the class resposible to maintain a querable form.
 * <p>
 * Do we need this to be asyncrhnous?
 * <p>
 * In theory we could just notify to the extencution that we had an update in the ET.
 **/
@Getter
public class NamedStreamView extends Observable implements View, Observer {

    private final WindowAssigner window_assigner;
    private Content content = new EmptyContent();

    public NamedStreamView(WindowAssigner wa) {
        this.window_assigner = wa;
    }

    @Override
    public void observerOf(Object observer) {
        ((Observable) observer).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;
        this.content = window_assigner.getContent(ts);
        setChanged();
        notifyObservers(ts);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }

}
