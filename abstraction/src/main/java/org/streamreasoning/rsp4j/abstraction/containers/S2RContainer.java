package org.streamreasoning.rsp4j.abstraction.containers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;

@RequiredArgsConstructor
@AllArgsConstructor
public  class S2RContainer<I, W> {
    @Getter
    private String sourceURI;
    @Getter
    private StreamToRelationOp<I, W> s2rOperator;
    @Getter
    private String tvgName;
}