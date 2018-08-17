package simple.querying;

import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionObserver;
import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.Triple;

import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
public class ContinuousQueryExecutionImpl extends ContinuousQueryExecutionObserver implements ContinuousQueryExecution {

    private final Dataset ds;
    private int i = 0;

    public ContinuousQueryExecutionImpl(SDS sds, Dataset ds, ContinuousQuery query) {
        super(sds, query);
        this.ds = ds;
    }

    public ContinuousQueryExecutionImpl(SDS sds, Dataset ds, ContinuousQuery query, RelationToStreamOperator s2r) {
        super(query, s2r, sds);
        this.ds = ds;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public String getQueryID() {
        return query.getID();
    }

    @Override
    public SDS getSDS() {
        return sds;
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

        SelectInstResponse r = new SelectInstResponse(query.getID() + "/ans/" + i, ts, triples, query);

        i++;
        return r;
    }


    @Override
    public void update(Observable o, Object arg) {
        InstantaneousResponse eval = eval((Long) arg);
        setChanged();
        notifyObservers(eval);
    }
}

