package it.polimi.yasper.core.quering.rspql.sds;

import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSBuilder {

    void visit(ContinuousQuery query);

    SDS getSDS();

    ContinuousQueryExecution getContinuousQueryExecution();

}
