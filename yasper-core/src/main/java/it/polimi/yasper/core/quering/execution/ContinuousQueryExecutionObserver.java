package it.polimi.yasper.core.quering.execution;

import it.polimi.yasper.core.quering.formatter.QueryResponseFormatter;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.querying.ContinuousQuery;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.quering.rspql.sds.SDS;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.IRI;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */

@AllArgsConstructor
@RequiredArgsConstructor
public abstract class ContinuousQueryExecutionObserver extends Observable implements Observer, ContinuousQueryExecution {

    protected final IRI id;
    protected final SDS sds;
    protected final ContinuousQuery query;
    protected TVGReasoner reasoner;
    protected RelationToStreamOperator s2r;


    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;

        InstantaneousResponse r = eval(ts);

        setChanged();
        notifyObservers(r);
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
    public String getQueryID() {
        return query.getID();
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

}
