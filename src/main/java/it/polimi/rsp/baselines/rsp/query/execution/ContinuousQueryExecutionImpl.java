package it.polimi.rsp.baselines.rsp.query.execution;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionImpl extends Observable implements ContinuousQueryExecution {
    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
