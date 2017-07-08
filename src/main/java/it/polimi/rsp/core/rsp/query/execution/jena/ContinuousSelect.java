package it.polimi.rsp.core.rsp.query.execution.jena;

import it.polimi.rsp.core.rsp.query.r2s.RelationToStreamOperator;
import it.polimi.rsp.core.rsp.query.reasoning.TVGReasoner;
import it.polimi.rsp.core.rsp.query.response.InstantaneousResponse;
import it.polimi.rsp.core.rsp.query.response.SelectResponse;
import it.polimi.rsp.core.rsp.sds.SDS;
import it.polimi.rsp.core.rsp.sds.windows.WindowOperator;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.ResultSetCheckCondition;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuousSelect extends ContinuousJenaQueryExecution {


    public ContinuousSelect(RSPQuery query, Query q, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, q, sds, reasoner, s2r);
    }

    @Override
    public void eval(SDS sds, WindowOperator stmt, long ts, RelationToStreamOperator r2s) {
        //TODO check if execution should be recreated

        if (stmt != null) {
        /*
        TODO if a two RSPQL eval on the same SDS I can use the stmt that triggerd the evaluation to discriminate if a query has to be evaluated or not
         */
        }

        this.execution = QueryExecutionFactory.create(q, sds);
        ResultSet results = execution.execSelect();
        last_response = new SelectResponse("http://streamreasoning.org/heaven/", query, results, ts);
        InstantaneousResponse eval = r2s.eval(last_response);
        setChanged();
        notifyObservers(eval);
    }


}
