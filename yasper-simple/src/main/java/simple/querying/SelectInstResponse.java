package simple.querying;

import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;
import it.polimi.yasper.core.spe.operators.r2r.ContinuousQuery;
import org.apache.commons.rdf.api.Triple;

import java.util.List;

public class SelectInstResponse extends InstantaneousResult {

    private final List<Triple> triples;

    public SelectInstResponse(String id, long cep_timestamp, List<Triple> triples, ContinuousQuery query) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        this.triples=triples;
    }

    @Override
    public InstantaneousResult difference(InstantaneousResult r) {
        return null;
    }

    @Override
    public InstantaneousResult intersection(InstantaneousResult new_response) {
        return null;
    }

    public List<Triple> getTriples() {
        return triples;
    }
}
