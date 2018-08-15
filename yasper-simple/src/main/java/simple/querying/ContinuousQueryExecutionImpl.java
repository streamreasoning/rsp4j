package simple.querying;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionObserver;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.Triple;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
public class ContinuousQueryExecutionImpl extends ContinuousQueryExecutionObserver implements ContinuousQueryExecution {

    private final Dataset ds;
    private int i = 0;

    public ContinuousQueryExecutionImpl(IRI id, SDS sds, Dataset ds, ContinuousQuery query) {
        super(id, sds, query);
        this.ds = ds;
    }

    public ContinuousQueryExecutionImpl(IRI id, SDS sds, Dataset ds, ContinuousQuery query, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(id, sds, query, reasoner, s2r);
        this.ds = ds;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return null;
    }

    @Override
    public String getQueryID() {
        return null;
    }

    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public void addFormatter(QueryResponseFormatter o) {
        addObserver(o);
    }

    @Override
    public void deleteFormatter(QueryResponseFormatter o) {
        deleteObserver(o);
    }

    @Override
    public InstantaneousResponse eval(long ts) {

        sds.materialize(ts);

        List<Triple> triples = ds.stream()
                .map(Quad::asTriple).collect(Collectors.toList());

        SelectInstResponse r = new SelectInstResponse(id.getIRIString() + "/ans/" + i, ts, triples, query);

        i++;
        return r;
    }


}

