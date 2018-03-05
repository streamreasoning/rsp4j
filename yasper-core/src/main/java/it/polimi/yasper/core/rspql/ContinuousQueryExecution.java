package it.polimi.yasper.core.rspql;

import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.spe.content.viewer.View;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution {

    InstantaneousResponse eval(long ts);

    ContinuousQuery getContinuousQuery();

    String getQueryID();

    SDS getSDS();

    void addFormatter(QueryResponseFormatter o);

    void deleteFormatter(QueryResponseFormatter o);

    void add(View item);
}

