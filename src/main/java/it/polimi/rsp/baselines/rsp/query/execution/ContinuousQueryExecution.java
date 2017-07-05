package it.polimi.rsp.baselines.rsp.query.execution;

import com.espertech.esper.client.EPStatement;
import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.baselines.rsp.sds.windows.WindowModel;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.InfGraph;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution extends QueryExecution, it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution {

    public void addObserver(Observer o);

    public void eval(SDS sds, EPStatement stm, long ts);

    public void eval(SDS sds, long ts);

    void materialize(WindowModel tvg);


}

