package it.polimi.yasper.core.sds;

import it.polimi.yasper.core.querying.ContinuousQueryExecution;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSManager {

    SDS build();

    ContinuousQueryExecution getContinuousQueryExecution();

}
