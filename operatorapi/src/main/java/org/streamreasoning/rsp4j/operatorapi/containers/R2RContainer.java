package org.streamreasoning.rsp4j.operatorapi.containers;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;

import java.util.Collections;
import java.util.List;


public  class R2RContainer<W, R> {
        private List<String> tvgName;
        private RelationToRelationOperator<W, R> r2rOperator;

    public R2RContainer() {
    }

    public R2RContainer(List<String> tvgName, RelationToRelationOperator<W, R> r2rOperator) {
        this.tvgName = tvgName;
        this.r2rOperator = r2rOperator;
    }
    public R2RContainer(String tvgName, RelationToRelationOperator<W, R> r2rOperator) {
        this(Collections.singletonList(tvgName),r2rOperator);
    }

    public List<String> getTvgNames() {
        return this.tvgName;
    }

    public RelationToRelationOperator<W, R> getR2rOperator() {
        return this.r2rOperator;
    }
}

