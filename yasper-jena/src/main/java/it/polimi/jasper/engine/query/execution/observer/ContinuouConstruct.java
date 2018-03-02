package it.polimi.jasper.engine.query.execution.observer;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.response.ConstructResponse;
import it.polimi.yasper.core.rspql.RelationToStreamOperator;
import it.polimi.yasper.core.rspql.SDS;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.QueryExecutionFactory;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuouConstruct extends JenaContinuousQueryExecution {

    public ContinuouConstruct(RSPQuery query, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, sds, reasoner, s2r);
    }

    @Override
    public InstantaneousResponse eval(long ts) {
        this.execution = QueryExecutionFactory.create(getQuery(), getDataset());
        this.last_response = new ConstructResponse("http://streamreasoning.org/yasper/", (RSPQuery) query, execution.execConstruct(), ts);
        return s2r.eval(last_response);
    }
}
