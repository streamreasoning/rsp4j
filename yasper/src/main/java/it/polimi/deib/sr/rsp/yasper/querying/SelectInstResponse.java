package it.polimi.deib.sr.rsp.yasper.querying;

import it.polimi.deib.sr.rsp.api.querying.result.SolutionMapping;
import it.polimi.deib.sr.rsp.api.querying.result.SolutionMappingBase;
import org.apache.commons.rdf.api.Triple;

public class SelectInstResponse extends SolutionMappingBase<Triple> {


    public SelectInstResponse(String id, long cep_timestamp, Triple triples) {
        super(id, System.currentTimeMillis(), cep_timestamp, triples);
    }

    @Override
    public SolutionMapping<Triple> difference(SolutionMapping<Triple> r) {
        return null;
    }

    @Override
    public SolutionMapping<Triple> intersection(SolutionMapping<Triple> new_response) {
        return null;
    }

}
