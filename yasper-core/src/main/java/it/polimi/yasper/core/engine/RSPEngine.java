package it.polimi.yasper.core.engine;


import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;


/**
 * @author Riccardo
 */
public interface RSPEngine extends EventProcessor<Stimulus> {

    ContinuousQueryExecution registerQuery(ContinuousQuery q);

    // TODO is reasoning enabled
    // TODO is external time control enabled
}
