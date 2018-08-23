package it.polimi.yasper.core.rspql.execution;

import it.polimi.yasper.core.rspql.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.rspql.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;
import lombok.AllArgsConstructor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
@AllArgsConstructor
public abstract class ContinuousQueryExecutionObserver extends Observable implements Observer, ContinuousQueryExecution {

    protected ContinuousQuery query;
    protected RelationToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query) {
        this.query = query;
        this.sds = sds;
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
    public String getQueryID() {
        return query.getID();
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

}
