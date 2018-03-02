package it.polimi.yasper.core.query.execution;

import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.RelationToStreamOperator;
import it.polimi.yasper.core.rspql.TimeVarying;
import it.polimi.yasper.core.reasoning.TVGReasoner;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionObserver extends Observable implements Observer, ContinuousQueryExecution {

    protected ContinuousQuery query;
    protected TVGReasoner reasoner;
    protected RelationToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        this.query = query;
        this.reasoner = reasoner;
        this.s2r = s2r;
        this.sds = sds;
    }

    @Override
    public String getQueryID() {
        return query.getID();
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public void add(TimeVarying item) {
        item.addObserver(this);
    }
}
