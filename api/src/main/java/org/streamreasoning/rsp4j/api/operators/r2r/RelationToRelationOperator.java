package org.streamreasoning.rsp4j.api.operators.r2r;


import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public interface RelationToRelationOperator<I, O> {

    //TODO this should not be time-aware
    Stream<O> eval(Stream<I> sds);

    TimeVarying<Collection<O>> apply(SDS<I> sds);

    SolutionMapping<O> createSolutionMapping(O result);

}
    