package it.polimi.yasper.core.query.execution;

import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import it.polimi.yasper.core.reasoning.TVGReasoner;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionImpl extends Observable implements ContinuousQueryExecution {

    protected ContinuousQuery query;
    protected TVGReasoner reasoner;
    protected RelationToStreamOperator s2r;

    public ContinuousQueryExecutionImpl(ContinuousQuery query, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        this.query = query;
        this.reasoner = reasoner;
        this.s2r = s2r;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void removeObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void eval(SDS sds, TimeVaryingGraph w, long ts) {
        eval(sds, w, ts, s2r);
    }

    @Override
    public String getQueryID() {
        return query.getID();
    }
}
