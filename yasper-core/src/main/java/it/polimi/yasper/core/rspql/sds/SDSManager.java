package it.polimi.yasper.core.rspql.sds;

import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSManager {

    SDS build();

    ContinuousQueryExecution getContinuousQueryExecution();

}
