package it.polimi.jasper.engine.query.execution.observer;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.response.SelectResponse;
import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.SDS;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuousSelect extends JenaContinuousQueryExecution {

    public ContinuousSelect(RSPQuery query, SDS sds, TVGReasoner reasoner, _ToStreamOperator s2r) {
        super(query, sds, reasoner, s2r);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r) {
        this.execution = QueryExecutionFactory.create(getQuery(), super.getDataset());
        ResultSet results = execution.execSelect();
        last_response = new SelectResponse("http://streamreasoning.org/heaven/", (RSPQuery) query, results, ts);
        return s2r.eval(last_response);
    }

}
