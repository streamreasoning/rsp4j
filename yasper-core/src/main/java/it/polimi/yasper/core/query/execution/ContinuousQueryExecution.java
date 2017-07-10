package it.polimi.yasper.core.query.execution;

import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.query.operators.s2r.WindowOperator;

import java.util.Observer;

/**
 * Created by Riccardo on 12/08/16.
 */

public interface ContinuousQueryExecution {

    public void addObserver(Observer o);

    public void removeObserver(Observer o);

    public void eval(SDS sds, WindowOperator w, long ts);

    public void eval(SDS sds, WindowOperator w, long ts, RelationToStreamOperator s2r);

    public void eval(SDS sds, long ts);

    public String getQueryID();
}

