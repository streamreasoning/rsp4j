package it.polimi.yasper.core.query.execution;

import it.polimi.yasper.core.jena.SDS;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.query.operators.s2r.WindowModel;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;

import java.util.Observer;

/**
 * Created by riccardo on 09/07/2017.
 */
public interface YasperQueryExecution extends it.polimi.heaven.rsp.rsp.querying.ContinousQueryExecution {

    public void addObserver(Observer o);

    public void eval(SDS sds, WindowOperator w, long ts);

    public void eval(SDS sds, WindowOperator w, long ts, RelationToStreamOperator s2r);

    public void eval(SDS sds, long ts);

    void materialize(WindowModel tvg);

}
