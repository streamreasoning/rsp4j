package org.streamreasoning.rsp4j.api.operators.r2r;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.stream.Stream;

public interface RelationToRelationOperator<T> {

    Stream<SolutionMapping<T>> eval(long ts);

}
