package it.polimi.jasper.engine.querying.execution.subscribers;

import it.polimi.jasper.engine.querying.response.ConstructResponse;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionSubscriber;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.*;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuouConstructSubscriber extends ContinuousQueryExecutionSubscriber {

    private final Query q;
    protected InstantaneousResponse last_response = null;
    protected QueryExecution execution;

    public ContinuouConstructSubscriber(ContinuousQuery query, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, sds, reasoner, s2r);
        this.q = QueryFactory.create(query.getSPARQL());
    }

    @Override
    public InstantaneousResponse eval(long ts) {
        this.execution = QueryExecutionFactory.create(q, (Dataset) sds);
        this.last_response = new ConstructResponse("http://streamreasoning.org/yasper/", query, execution.execConstruct(), ts);
        return s2r.eval(last_response);
    }

}
