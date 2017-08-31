package it.polimi.rspql;


import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.stream.RegisteredStream;
import it.polimi.yasper.core.stream.StreamItem;
import it.polimi.yasper.core.utils.QueryConfiguration;


/**
 * @author Riccardo
 */
public interface RSPEngine<S extends Stream, R extends RegisteredStream> {

    R register(S s);

    void unregister(R s);

    ContinuousQuery parseQuery(String input);

    ContinuousQueryExecution register(ContinuousQuery q, QueryConfiguration c);

    ContinuousQueryExecution register(String q, QueryConfiguration c);

    void unregister(ContinuousQuery qId);

    void register(ContinuousQuery q, QueryResponseFormatter o);

    void unregister(ContinuousQuery q, QueryResponseFormatter o);

    void register(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    void unregister(ContinuousQueryExecution cqe, QueryResponseFormatter o);

    boolean process(StreamItem var1);

    void startProcessing();

    void stopProcessing();


    // TODO is reasoning enabled
    // TODO is external time control enabled
}
