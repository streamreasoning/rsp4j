package simple.querying;

import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.querying.result.SolutionMappingBase;
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
