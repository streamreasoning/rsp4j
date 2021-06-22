package org.streamreasoning.rsp4j.api.querying;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.stream.Stream;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution<I, W, R, O> {

    DataStream<O> outstream();

    ContinuousQuery query();

    SDS<W> sds();

    StreamToRelationOp<I, W>[] s2rs();

    RelationToRelationOperator<W, R> r2r();

    RelationToStreamOperator<R,O> r2s();

    void add(StreamToRelationOp<I, W> op);

    Stream<SolutionMapping<R>> eval(Long now);
}

