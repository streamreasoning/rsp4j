package org.streamreasoning.rsp4j.yasper.querying;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMappingBase;

public class SelectInstResponse<T> extends SolutionMappingBase<T> {


    public SelectInstResponse(String id, long cep_timestamp, T triples) {
        super(id, System.currentTimeMillis(), cep_timestamp, triples);
    }

    @Override
    public SolutionMapping<T> difference(SolutionMapping<T> r) {
        return null;
    }

    @Override
    public SolutionMapping<T> intersection(SolutionMapping<T> new_response) {
        return null;
    }

}
