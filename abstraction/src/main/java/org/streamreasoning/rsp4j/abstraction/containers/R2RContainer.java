package org.streamreasoning.rsp4j.abstraction.containers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;


    @RequiredArgsConstructor
    @AllArgsConstructor
    public  class R2RContainer<W, R> {
        @Getter
        private String tvgName;
        @Getter
        private RelationToRelationOperator<W, R> r2rOperator;

    }

