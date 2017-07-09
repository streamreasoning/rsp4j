package it.polimi.yasper.core.query.execution;

import it.polimi.sr.rsp.RSPQuery;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.polimi.yasper.core.jena.SDS;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionImpl extends Observable implements ContinuousQueryExecution {
    protected RSPQuery query;
    protected SDS sds;
    protected TVGReasoner reasoner;
    protected RelationToStreamOperator s2r;

    public ContinuousQueryExecutionImpl(RSPQuery query, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        this.query = query;
        this.sds = sds;
        this.reasoner = reasoner;
        this.s2r = s2r;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public void eval(SDS sds, WindowOperator w, long ts) {
        eval(sds, w, ts, s2r);
    }
}
