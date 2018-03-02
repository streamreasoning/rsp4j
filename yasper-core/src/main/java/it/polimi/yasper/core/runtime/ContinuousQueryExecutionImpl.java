package it.polimi.yasper.core.runtime;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.rspql.TimeVarying;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
@AllArgsConstructor
public class ContinuousQueryExecutionImpl extends Observable implements Observer, ContinuousQueryExecution {

    private SDSImpl sds;
    private ContinuousQuery query;
    private StreamOperator r2S;

    @Override
    public InstantaneousResponse eval(long ts) {

        //TODO loop through the SDS quads

        List<Graph> graphs = sds.getallGraph();

        List<Triple> triples = graphs.stream().flatMap(Graph::stream).collect(Collectors.toList());

        SelectInstResponse r = new SelectInstResponse("1", ts, triples, query);

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
    public void add(TimeVarying item) {
        item.addObserver(this);
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

