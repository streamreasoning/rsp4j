package org.streamreasoning.rsp4j.api.querying.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolutionMappingBase<I> implements SolutionMapping<I> {

    private String id;
    private long creation_timestamp, cep_timestamp;
    private I element;

    public SolutionMappingBase(I element, long creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
        this.element = element;
    }

    @Override
    public I get() {
        return element;
    }

    @Override
    public SolutionMapping<I> difference(SolutionMapping<I> r) {
        return null;
    }

    @Override
    public SolutionMapping<I> intersection(SolutionMapping<I> new_response) {
        return null;
    }

    @Override
    public long getCreationTime() {
        return creation_timestamp;
    }


}
