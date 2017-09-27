package it.polimi.yasper.core.query.execution;

import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.reasoning.TVGReasoner;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionObserver extends Observable implements Observer, ContinuousQueryExecution {

    protected ContinuousQuery query;
    protected TVGReasoner reasoner;
    protected _ToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query, TVGReasoner reasoner, _ToStreamOperator s2r) {
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
