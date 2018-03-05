package it.polimi.yasper.core.simple.querying;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.spe.content.viewer.View;
import lombok.NonNull;
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

    @NonNull
    private RDF rdf;
    @NonNull
    private IRI id;
    @NonNull
    private Dataset ds;
    @NonNull
    private SDS sds;
    @NonNull
    private ContinuousQuery query;
    @NonNull
    private StreamOperator r2S;
    private int i = 0;

    @Override
    public InstantaneousResponse eval(long ts) {

        sds.eval(ts);

        List<Triple> triples = ds.stream()
                .filter(quad -> quad.getGraphName().isPresent())
                .filter(quad -> quad.getGraphName().get().equals(rdf.createIRI("w0")))
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
    public void add(View item) {
        item.addObservable(this);
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

