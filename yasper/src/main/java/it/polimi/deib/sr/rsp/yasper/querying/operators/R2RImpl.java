package it.polimi.deib.sr.rsp.yasper.querying.operators;

import it.polimi.deib.sr.rsp.yasper.querying.SelectInstResponse;
import it.polimi.deib.sr.rsp.api.operators.r2r.RelationToRelationOperator;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.querying.result.SolutionMapping;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.Triple;

import java.util.stream.Stream;

public class R2RImpl implements RelationToRelationOperator<Triple> {

    private final SDS sds;
    private final ContinuousQuery query;
    private final Dataset ds;

    public R2RImpl(SDS sds, ContinuousQuery query) {
        this.sds = sds;
        this.query = query;
        this.ds = (Dataset) sds;
    }

    @Override
    public Stream<SolutionMapping<Triple>> eval(long ts) {
        sds.materialize(ts);

        return ds.stream()
                .map(Quad::asTriple).map(triple -> new SelectInstResponse(query.getID() + "/ans/" + ts, ts, triple));

    }
}
