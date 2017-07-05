package it.polimi.rsp.baselines.rsp.query.execution;

import com.espertech.esper.client.EPStatement;
import it.polimi.rsp.baselines.rsp.query.reasoning.TVGReasoner;
import it.polimi.rsp.baselines.rsp.query.response.ConstructResponse;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.reasoner.Reasoner;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuouConstruct extends ContinuousJenaQueryExecution {
    public ContinuouConstruct(RSPQuery query, SDS sds, TVGReasoner reasoner) {
        super(query, sds, reasoner);
    }

    @Override
    public void eval(SDS sds, EPStatement stmt, long ts) {
        if (stmt != null) {
        /*
        TODO if a two RSPQL eval on the same SDS I can use the stmt that triggerd the evaluation to discriminate if a query has to be evaluated or not
         */
        }

        this.execution = QueryExecutionFactory.create(q, sds);
        last_response = new ConstructResponse("http://streamreasoning.org/heaven/", query, execution.execConstruct(), ts);
        setChanged();
        notifyObservers(last_response);
    }

}
