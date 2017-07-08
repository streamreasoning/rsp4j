package it.polimi.rsp.core.rsp.query.execution;

import it.polimi.rsp.core.rsp.query.r2s.RelationToStreamOperator;
import it.polimi.rsp.core.rsp.sds.SDS;
import it.polimi.rsp.core.rsp.sds.windows.WindowModel;
import it.polimi.rsp.core.rsp.sds.windows.WindowOperator;
import org.apache.jena.query.QueryExecution;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution extends QueryExecution, it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution {

    public void addObserver(Observer o);

    public void eval(SDS sds, WindowOperator w, long ts);

    public void eval(SDS sds, WindowOperator w, long ts, RelationToStreamOperator s2r);

    public void eval(SDS sds, long ts);

    void materialize(WindowModel tvg);


}

