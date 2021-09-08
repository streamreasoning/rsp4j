package org.streamreasoning.rsp4j.abstraction.containers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

    @RequiredArgsConstructor
    @AllArgsConstructor
    public  class AggregationContainer<R> {
        @Getter
        private String tvgName;
        @Getter
        private String functionName;
        @Getter
        private String inputVariable;
        @Getter
        private String outputVariable;

    }

