package it.polimi.yasper.core.quering.execution;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.spe.content.viewer.View;

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

