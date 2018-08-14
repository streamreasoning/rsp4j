package simple.windowing;

import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.windowing.assigner.CSPARQLWindowAssigner;

import java.util.Observable;
import java.util.Observer;

public class DefaultStreamView extends Observable implements View, Observer {

    private Content content;

    @Override
    public void observerOf(Object observer) {
        ((CSPARQLWindowAssigner) observer).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;
        WindowAssigner window_assigner = (WindowAssigner) o;
        this.content = window_assigner.getContent(ts);
        setChanged();
        notifyObservers(ts);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }


}
