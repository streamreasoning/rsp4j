package it.polimi.runtime;

import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.AllArgsConstructor;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

@AllArgsConstructor
public class ContinuousQueryExecutionImpl implements ContinuousQueryExecution {

    private SDS sds;
    private ContinuousQuery query;
    private StreamOperator r2S;

    @Override
    public InstantaneousResponse eval(long ts) {
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds) {
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q) {
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner) {
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r) {
        return null;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return null;
    }

    @Override
    public String getQueryID() {
        return null;
    }

    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public _ToStreamOperator getRelationToStreamOperator() {
        return null;
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void deleteObserver(Observer o) {

    }

    @Override
    public void add(TimeVarying item) {

    }
}

