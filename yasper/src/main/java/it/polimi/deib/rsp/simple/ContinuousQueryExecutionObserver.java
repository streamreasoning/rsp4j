package it.polimi.deib.rsp.simple;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import lombok.AllArgsConstructor;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
@AllArgsConstructor
public abstract class ContinuousQueryExecutionObserver extends Observable implements Observer, ContinuousQueryExecution<Triple, Triple, Triple> {

    protected ContinuousQuery query;
    protected RelationToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query) {
        this.query = query;
        this.sds = sds;
    }


    @Override
    public void add(QueryResultFormatter o) {
        addObserver(o);
    }

    @Override
    public void remove(QueryResultFormatter o) {
        deleteObserver(o);
    }

}
