package org.streamreasoning.rsp4j.api.operators.s2r;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;


public interface StreamToRelationOperatorFactory<I, O> {

    StreamToRelationOp<I, O> build(long a, long b, long t0);

}
