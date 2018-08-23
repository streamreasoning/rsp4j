package it.polimi.yasper.core.rspql.execution;

import it.polimi.yasper.core.rspql.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.rspql.response.InstantaneousResponse;
import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.rspql.sds.SDS;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution extends Observer {

    InstantaneousResponse eval(long ts);

    ContinuousQuery getContinuousQuery();

    String getQueryID();

    SDS getSDS();

    void addFormatter(QueryResponseFormatter o);

    void deleteFormatter(QueryResponseFormatter o);



}

