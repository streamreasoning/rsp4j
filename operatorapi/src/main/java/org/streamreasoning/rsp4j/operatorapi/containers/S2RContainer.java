package org.streamreasoning.rsp4j.operatorapi.containers;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;

public  class S2RContainer<I, W> {
    private String sourceURI;
    private StreamToRelationOp<I, W> s2rOperator;
    private String tvgName;

    public S2RContainer() {
    }

    public S2RContainer(String sourceURI, StreamToRelationOp<I, W> s2rOperator, String tvgName) {
        this.sourceURI = sourceURI;
        this.s2rOperator = s2rOperator;
        this.tvgName = tvgName;
    }

    public String getSourceURI() {
        return this.sourceURI;
    }

    public StreamToRelationOp<I, W> getS2rOperator() {
        return this.s2rOperator;
    }

    public String getTvgName() {
        return this.tvgName;
    }
}