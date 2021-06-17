package org.streamreasoning.rsp4j.abstraction.functions;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.Collection;

public interface AggregationFunction<T> {

    SolutionMapping<T> evaluate(String variableName, String outputName, Collection<SolutionMapping<T>> collection);

}
