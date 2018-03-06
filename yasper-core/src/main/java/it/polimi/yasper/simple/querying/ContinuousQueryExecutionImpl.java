package it.polimi.yasper.simple.querying;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.SDS;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
@RequiredArgsConstructor
public class ContinuousQueryExecutionImpl extends Observable implements Observer, ContinuousQueryExecution {

    private final RDF rdf;
    private final IRI id;
    private final Dataset ds;
    private final SDS sds;
    private final ContinuousQuery query;
    private int i = 0;

    @Override
    public InstantaneousResponse eval(long ts) {

        sds.eval(ts);

        List<Triple> triples = ds.stream()
                .map(Quad::asTriple).collect(Collectors.toList());

        SelectInstResponse r = new SelectInstResponse(id.getIRIString() + "/ans/" + i, ts, triples, query);

        i++;
        return r;
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
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;

        sds.beforeEval();
        InstantaneousResponse r = eval(ts);
        sds.afterEval();

        setChanged();
        notifyObservers(r);
    }
}

