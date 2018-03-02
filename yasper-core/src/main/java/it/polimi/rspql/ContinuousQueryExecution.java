package it.polimi.rspql;

import it.polimi.yasper.core.query.response.InstantaneousResponse;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution {

    InstantaneousResponse eval(long ts);

    ContinuousQuery getContinuousQuery();

    String getQueryID();

    SDS getSDS();

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    void add(TimeVarying item);
}

