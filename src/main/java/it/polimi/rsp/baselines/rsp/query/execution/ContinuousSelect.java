package it.polimi.rsp.baselines.rsp.query.execution;

import com.espertech.esper.client.EPStatement;
import it.polimi.rsp.baselines.rsp.query.response.SelectResponse;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.sr.rsp.RSPQuery;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.reasoner.Reasoner;

/**
 * Created by riccardo on 03/07/2017.
 */
public class ContinuousSelect extends ContinuousJenaQueryExecution {

    public ContinuousSelect(RSPQuery query, SDS sds, Reasoner reasone) {
        super(query, sds,reasone);
    }

    @Override
    public void eval(SDS sds, EPStatement stmt, long ts) {
        //TODO check if execution should be recreated

        if (stmt != null) {
        /*
        TODO if a two RSPQL eval on the same SDS I can use the stmt that triggerd the evaluation to discriminate if a query has to be evaluated or not
         */
        }

        this.execution = QueryExecutionFactory.create(q, sds);
        last_response = new SelectResponse("http://streamreasoning.org/heaven/", query, execution.execSelect(), ts);

        setChanged();
        notifyObservers(last_response);
    }


}
