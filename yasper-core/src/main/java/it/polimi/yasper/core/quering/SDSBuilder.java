package it.polimi.yasper.core.quering;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;

/**
 * Created by riccardo on 05/09/2017.
 */
public interface SDSBuilder {

    void visit(ContinuousQuery query);

    SDS getSDS();

    ContinuousQueryExecution getContinuousQueryExecution();

}
