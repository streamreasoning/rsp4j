package it.polimi.yasper.core.engine;


import it.polimi.streaming.EventProcessor;
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

    Stream register(Stream s);

    void unregister(Stream s);

    ContinuousQuery parseQuery(String input);

    ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c);

    ContinuousQueryExecution register(String q, QueryConfiguration c);

    void unregister(ContinuousQuery qId);

    void register(ContinuousQuery q, QueryResponseFormatter o);

    void unregister(ContinuousQuery q, QueryResponseFormatter o);


    // TODO is reasoning enabled
    // TODO is external time control enabled
}
