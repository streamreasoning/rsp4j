package org.streamreasoning.rsp4j.api.querying.result;

import org.apache.commons.rdf.api.Triple;

import java.util.function.Function;

public interface SolutionMapping<I> {

    long getCreationTime();

    I get();

    SolutionMapping<I> difference(SolutionMapping<I> r);

    SolutionMapping<I> intersection(SolutionMapping<I> new_response);

    default <O> SolutionMapping<O> map(Function<? super SolutionMapping<I>, ? extends SolutionMapping<O>> mapper) {
        return mapper.apply(this);
    }

}
