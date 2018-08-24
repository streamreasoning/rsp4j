package it.polimi.yasper.core.spe.operators.r2r.execution;

import it.polimi.yasper.core.spe.operators.r2s.result.QueryResultFormatter;
import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution extends Observer {

    InstantaneousResult eval(long ts);

    ContinuousQuery getContinuousQuery();

    String getQueryID();

    SDS getSDS();

    void add(QueryResultFormatter o);

    void remove(QueryResultFormatter o);


}

