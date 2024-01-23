package org.streamreasoning.rsp4j.abstraction.containers;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;


public  class R2SContainer<R, O> {
        private String sinkURI;
        private RelationToStreamOperator<R, O> r2sOperator;

    public R2SContainer() {
    }

    public R2SContainer(String sinkURI, RelationToStreamOperator<R, O> r2sOperator) {
        this.sinkURI = sinkURI;
        this.r2sOperator = r2sOperator;
    }

    public String getSinkURI() {
        return this.sinkURI;
    }

    public RelationToStreamOperator<R, O> getR2sOperator() {
        return this.r2sOperator;
    }
}

