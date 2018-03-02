package it.polimi.runtime;

import it.polimi.rspql.RelationToStreamOperator;
import it.polimi.rspql.ContinuousQuery;
import it.polimi.rspql.ContinuousQueryExecution;
import it.polimi.rspql.SDS;
import it.polimi.rspql.TimeVarying;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
@AllArgsConstructor
public class ContinuousQueryExecutionImpl extends Observable implements Observer, ContinuousQueryExecution {

    private SDS sds;
    private ContinuousQuery query;
    private StreamOperator r2S;

    @Override
    public InstantaneousResponse eval(long ts) {
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
    public void addObserver(Observer o) {

    }

    @Override
    public void deleteObserver(Observer o) {

    }

    @Override
    public void add(TimeVarying item) {
        item.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;

        sds.beforeEval();
        InstantaneousResponse r = eval(ts);
        sds.afterEval();

        //TODO

        setChanged();
        notifyObservers(r);
    }
}

