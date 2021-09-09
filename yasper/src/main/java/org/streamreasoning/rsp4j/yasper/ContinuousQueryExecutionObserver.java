package org.streamreasoning.rsp4j.yasper;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousQueryExecutionObserver<I, W, R, O> extends Observable implements Observer, ContinuousQueryExecution<I, W, R, O> {

    protected ContinuousQuery query;
    protected RelationToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query) {
        this.query = query;
        this.sds = sds;
    }

    public ContinuousQueryExecutionObserver(ContinuousQuery query, RelationToStreamOperator s2r, SDS sds) {
        this.query = query;
        this.s2r = s2r;
        this.sds = sds;
    }
}
