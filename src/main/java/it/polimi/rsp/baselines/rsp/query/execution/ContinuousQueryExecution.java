package it.polimi.rsp.baselines.rsp.query.execution;

import com.espertech.esper.client.EPStatement;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution extends QueryExecution, it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution {

    public void addObserver(Observer o);

    public void eval(SDS sds, EPStatement stm, long ts);

    public void eval(SDS sds, long ts);

    public void bindTbox(Model tbox);

    void materialize(TimeVaryingGraph tvg);

}

