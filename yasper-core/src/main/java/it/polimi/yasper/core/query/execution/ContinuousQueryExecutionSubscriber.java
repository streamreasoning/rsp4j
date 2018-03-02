package it.polimi.yasper.core.query.execution;

import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.polimi.yasper.core.rspql.*;
import it.polimi.yasper.core.spe.windowing.assigner.WindowAssigner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Observable;

/**
 * Created by riccardo on 05/07/2017.
 */
@Log4j
@Getter
@AllArgsConstructor
public abstract class ContinuousQueryExecutionSubscriber extends Observable implements ContinuousQueryExecution {

    protected ContinuousQuery query;
    protected SDS sds;
    protected TVGReasoner reasoner;
    protected RelationToStreamOperator s2r;

    public synchronized void update(WindowAssigner stmt, Long ts) {
        sds.beforeEval();
        InstantaneousResponse eval = eval(ts);
        sds.afterEval();

        setChanged();
        notifyObservers(eval);
    }

    @Override
    public void addFormatter(QueryResponseFormatter o) {
        addObserver(o);
    }

    @Override
    public void deleteFormatter(QueryResponseFormatter o) {
        deleteObserver(o);
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public String getQueryID() {
        return query.getID();
    }

    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public void add(TimeVarying item) {
        //TODO item will be an extended statement and this is the actual execution
        //need to remove Observer
        item.addObserver(null);
    }

}


