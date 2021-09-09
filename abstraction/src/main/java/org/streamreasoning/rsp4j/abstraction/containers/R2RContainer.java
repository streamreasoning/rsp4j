package org.streamreasoning.rsp4j.abstraction.containers;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;


public  class R2RContainer<W, R> {
        private String tvgName;
        private RelationToRelationOperator<W, R> r2rOperator;

    public R2RContainer() {
    }

    public R2RContainer(String tvgName, RelationToRelationOperator<W, R> r2rOperator) {
        this.tvgName = tvgName;
        this.r2rOperator = r2rOperator;
    }

    public String getTvgName() {
        return this.tvgName;
    }

    public RelationToRelationOperator<W, R> getR2rOperator() {
        return this.r2rOperator;
    }
}

