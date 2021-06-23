package org.streamreasoning.rsp4j.abstraction.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class AggregationFunctionRegistry {

    private static AggregationFunctionRegistry INSTANCE;
    private Map<String, AggregationFunction<?>> nameToFunctionHolder;

    private AggregationFunctionRegistry() {
        nameToFunctionHolder = new HashMap<>();
    }

    public static synchronized AggregationFunctionRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AggregationFunctionRegistry();
        }
        return INSTANCE;
    }

    public void addFunction(String functionName, AggregationFunction function) {
        nameToFunctionHolder.put(functionName, function);
    }

    public <T> Optional<AggregationFunction<T>> getFunction(String functionName) {
        AggregationFunction<T> aggregationFunction = (AggregationFunction<T>) nameToFunctionHolder.get(functionName);
        return Optional.ofNullable(aggregationFunction);
    }
}
