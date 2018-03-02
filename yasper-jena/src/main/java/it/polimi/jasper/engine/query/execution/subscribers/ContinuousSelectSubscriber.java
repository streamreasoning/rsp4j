package it.polimi.jasper.engine.query.execution.subscribers;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.response.ConstructResponse;
import it.polimi.yasper.core.rspql.RelationToStreamOperator;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecutionSubscriber;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuousSelectSubscriber extends ContinuousQueryExecutionSubscriber {

    protected InstantaneousResponse last_response = null;
    protected QueryExecution execution;

    public ContinuousSelectSubscriber(ContinuousQuery query, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, sds, reasoner, s2r);
    }


    @Override
    public InstantaneousResponse eval(long ts) {
        this.execution = QueryExecutionFactory.create(((RSPQuery) query).getQ(), (Dataset) sds);
        this.last_response = new ConstructResponse("http://streamreasoning.org/yasper/", (RSPQuery) query, execution.execConstruct(), ts);
        return s2r.eval(last_response);
    }

}
