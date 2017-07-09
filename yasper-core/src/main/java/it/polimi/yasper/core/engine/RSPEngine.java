package it.polimi.yasper.core.engine;


import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.utils.QueryConfiguration;


/**
 * @author Riccardo
 */
public interface RSPEngine extends EventProcessor<Stimulus> {


    ContinuousQueryExecution registerQuery(ContinuousQuery q);

    ContinuousQueryExecution registerQuery(ContinuousQuery q, QueryConfiguration c);


    // TODO is reasoning enabled
    // TODO is external time control enabled
}
