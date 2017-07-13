package it.polimi.yasper.core.engine;


import it.polimi.streaming.EventProcessor;
import it.polimi.streaming.Stimulus;
import it.polimi.yasper.core.query.ContinuousQuery;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.QueryConfiguration;


/**
 * @author Riccardo
 */
public interface RSPEngine extends EventProcessor<StreamItem> {

    void registerStream(Stream s);

    void unregisterStream(String s);

    ContinuousQuery parseQuery(String input);

    ContinuousQueryExecution registerQuery(ContinuousQuery q, QueryConfiguration c);

    ContinuousQueryExecution registerQuery(String q, QueryConfiguration c);

    void unregisterQuery(String qId);

    void registerObserver(String q, QueryResponseFormatter o);

    void unregisterObserver(String q, QueryResponseFormatter o);





    // TODO is reasoning enabled
    // TODO is external time control enabled
}
