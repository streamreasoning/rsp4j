package org.streamreasoning.rsp4j.abstraction.containers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;


    @RequiredArgsConstructor
    @AllArgsConstructor
    public  class R2SContainer<R, O> {
        @Getter
        private String sinkURI;
        @Getter
        private RelationToStreamOperator<R, O> r2sOperator;

    }

