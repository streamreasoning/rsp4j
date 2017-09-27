package it.polimi.jasper.engine.query.execution.subscribers;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.response.ConstructResponse;
import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.SDS;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecutionSubscriber;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuouConstructSubscriber extends ContinuousQueryExecutionSubscriber {

    protected InstantaneousResponse last_response = null;
    protected QueryExecution execution;

    public ContinuouConstructSubscriber(ContinuousQuery query, SDS sds, TVGReasoner reasoner, _ToStreamOperator s2r) {
        super(query, sds, reasoner, s2r);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r) {
        this.execution = QueryExecutionFactory.create(((RSPQuery) q).getQ(), (Dataset) sds);
        this.last_response = new ConstructResponse("http://streamreasoning.org/yasper/", (RSPQuery) query, execution.execConstruct(), ts);
        return s2r.eval(last_response);
    }

}
