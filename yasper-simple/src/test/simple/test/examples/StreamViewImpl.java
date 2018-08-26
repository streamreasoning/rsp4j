package simple.test.examples;

import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;
import it.polimi.yasper.core.spe.operators.r2s.result.QueryResultFormatter;
import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;

import java.util.Observable;
import java.util.Observer;

public class StreamViewImpl extends Observable implements ContinuousQueryExecution, Observer {

    private Content content;

    @Override
    public void update(Observable o, Object arg) {
        WindowAssigner window_assigner = (WindowAssigner) o;
        this.content = window_assigner.getContent((Long) arg);
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }


    @Override
    public InstantaneousResult eval(long ts) {
        return null;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return null;
    }

    @Override
    public String getQueryID() {
        return null;
    }

    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public void remove(QueryResultFormatter o) {

    }

    @Override
    public void add(QueryResultFormatter o) {

    }

}
