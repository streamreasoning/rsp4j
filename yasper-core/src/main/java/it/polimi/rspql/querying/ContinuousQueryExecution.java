package it.polimi.rspql.querying;

import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution {

    InstantaneousResponse eval(long ts);

    InstantaneousResponse eval(long ts, SDS sds);

    InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q);

    InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner);

    InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r);

    ContinuousQuery getContinuousQuery();

    String getQueryID();

    SDS getSDS();

    _ToStreamOperator getRelationToStreamOperator();

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    void add(TimeVarying item);
}

