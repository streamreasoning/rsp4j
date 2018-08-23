package simple.querying;

import it.polimi.yasper.core.rspql.response.InstantaneousResponse;
import it.polimi.yasper.core.rspql.querying.ContinuousQuery;
import org.apache.commons.rdf.api.Triple;

import java.util.List;

public class SelectInstResponse extends InstantaneousResponse{

    private final List<Triple> triples;

    public SelectInstResponse(String id, long cep_timestamp, List<Triple> triples, ContinuousQuery query) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        this.triples=triples;
    }

    @Override
    public InstantaneousResponse difference(InstantaneousResponse r) {
        return null;
    }

    @Override
    public InstantaneousResponse intersection(InstantaneousResponse new_response) {
        return null;
    }

    public List<Triple> getTriples() {
        return triples;
    }
}
