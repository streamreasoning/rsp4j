package it.polimi.yasper.core.querying;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.stream.data.WebDataStream;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution<I, E1, E2> {

    <O> WebDataStream<O> outstream();

    ContinuousQuery getContinuousQuery();

    SDS<E1> getSDS();

    StreamToRelationOperator<I, E1>[] getS2R();

    RelationToRelationOperator<E2> getR2R();

    RelationToStreamOperator<E2> getR2S();

    void add(QueryResultFormatter o);

    void remove(QueryResultFormatter o);

}

