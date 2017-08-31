package it.polimi.rspql;

import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSBuilder<Q extends ContinuousQuery> {

    void visit(Q query);

    SDS getSDS();

    Q getContinuousQuery();

    ContinuousQueryExecution getContinuousQueryExecution();

}
