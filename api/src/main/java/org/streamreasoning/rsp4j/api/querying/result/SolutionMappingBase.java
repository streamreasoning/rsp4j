package org.streamreasoning.rsp4j.api.querying.result;

public class SolutionMappingBase<I> implements SolutionMapping<I> {

    private String id;
    private long creation_timestamp, cep_timestamp;
    private I element;

    public SolutionMappingBase(I element, long creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
        this.element = element;
    }

    public SolutionMappingBase(String id, long creation_timestamp, long cep_timestamp, I element) {
        this.id = id;
        this.creation_timestamp = creation_timestamp;
        this.cep_timestamp = cep_timestamp;
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


    public String getId() {
        return this.id;
    }

    public long getCreation_timestamp() {
        return this.creation_timestamp;
    }

    public long getCep_timestamp() {
        return this.cep_timestamp;
    }

    public I getElement() {
        return this.element;
    }
}
