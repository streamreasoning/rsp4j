package org.streamreasoning.rsp4j.api.operators.r2s;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.stream.Stream;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator<R,O> {

    O eval(SolutionMapping<R> sm, long ts);

    default Stream<O> eval(Stream<SolutionMapping<R>> sml, long ts) {
        return sml.map(sm -> eval(sm, ts));
    }
}
