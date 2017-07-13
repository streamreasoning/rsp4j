package it.polimi.jasper.engine.query.execution;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.response.SelectResponse;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuousSelect extends ContinuousJenaQueryExecution {

    public ContinuousSelect(RSPQuery query, Query q, JenaSDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, q, sds, reasoner, s2r);
    }

    @Override
    public void eval(SDS sds, TimeVaryingGraph stmt, long ts, RelationToStreamOperator r2s) {
        //TODO check if execution should be recreated

        if (stmt != null) {
        /*
        TODO if a two RSPQL eval on the same SDS I can use the stmt that triggerd the evaluation to discriminate if a query has to be evaluated or not
         */
        }

        this.execution = QueryExecutionFactory.create(q, getDataset());
        ResultSet results = execution.execSelect();
        last_response = new SelectResponse("http://streamreasoning.org/heaven/", (RSPQuery) query, results, ts);
        InstantaneousResponse eval = r2s.eval(last_response);
        setChanged();
        notifyObservers(eval);
    }


}
