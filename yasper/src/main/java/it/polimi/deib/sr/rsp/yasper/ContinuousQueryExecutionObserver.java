package it.polimi.deib.sr.rsp.yasper;

import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import it.polimi.deib.sr.rsp.api.operators.r2s.RelationToStreamOperator;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import lombok.AllArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */
@AllArgsConstructor
public abstract class ContinuousQueryExecutionObserver<I, E1, E2> extends Observable implements Observer, ContinuousQueryExecution<I, E1, E2> {

    protected ContinuousQuery query;
    protected RelationToStreamOperator s2r;
    protected SDS sds;

    public ContinuousQueryExecutionObserver(SDS sds, ContinuousQuery query) {
        this.query = query;
        this.sds = sds;
    }

}
