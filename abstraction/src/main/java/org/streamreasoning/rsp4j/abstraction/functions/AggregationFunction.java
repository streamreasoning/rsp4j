package org.streamreasoning.rsp4j.abstraction.functions;

import java.util.Collection;

public interface AggregationFunction<T> {

    T evaluate(String variableName, String outputName, Collection<T> collection);

}
