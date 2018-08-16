package it.polimi.yasper.core.quering.execution;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
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
    protected RelationToStreamOperator s2r;

    public synchronized void update(WindowAssigner stmt, Long ts) {
        InstantaneousResponse eval = eval(ts);

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

}


