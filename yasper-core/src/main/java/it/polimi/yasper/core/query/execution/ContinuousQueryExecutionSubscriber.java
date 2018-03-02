package it.polimi.yasper.core.query.execution;

import it.polimi.rspql.RelationToStreamOperator;
import it.polimi.rspql.ContinuousQuery;
import it.polimi.rspql.ContinuousQueryExecution;
import it.polimi.rspql.SDS;
import it.polimi.rspql.TimeVarying;
import it.polimi.spe.windowing.assigner.WindowAssigner;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Observable;
import java.util.Observer;

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
    public void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void add(TimeVarying item) {
        //TODO item will be an extended statement and this is the actual execution
        //need to remove Observer
        item.addObserver(null);
    }
}


