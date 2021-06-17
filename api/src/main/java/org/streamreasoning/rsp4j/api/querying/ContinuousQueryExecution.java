package org.streamreasoning.rsp4j.api.querying;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;

import java.util.stream.Stream;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution<I, E1, O> {

    WebDataStream<O> outstream();

    ContinuousQuery query();

    SDS<E1> sds();

    StreamToRelationOp<I, E1>[] s2rs();

    RelationToRelationOperator<E1, O> r2r();

    RelationToStreamOperator<O> r2s();

    void add(StreamToRelationOp<I, E1> op);

    Stream<SolutionMapping<O>> eval(Long now);
}

