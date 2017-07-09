package it.polimi.yasper.core.jena.query.execution;

import it.polimi.sr.rsp.RSPQuery;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.polimi.yasper.core.jena.query.response.ConstructResponse;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.jena.SDS;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecutionFactory;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuouConstruct extends ContinuousJenaQueryExecution {

    public ContinuouConstruct(RSPQuery query, Query q, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, q, sds, reasoner, s2r);
    }

    @Override
    public void eval(SDS sds, WindowOperator stmt, long ts, RelationToStreamOperator r2s) {
        if (stmt != null) {
        /*
        TODO if a two RSPQL eval on the same SDS I can use the stmt that triggerd the evaluation to discriminate if a query has to be evaluated or not
         */
        }

        this.execution = QueryExecutionFactory.create(q, sds);
        this.last_response = new ConstructResponse("http://streamreasoning.org/heaven/", query, execution.execConstruct(), ts);
        InstantaneousResponse eval = r2s.eval(last_response);

        setChanged();
        notifyObservers(eval);
    }

}
