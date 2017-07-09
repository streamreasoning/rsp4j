package it.polimi.yasper.core.query;

import it.polimi.yasper.core.query.operators.s2r.WindowModel;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface TimeVaryingGraph {

    public long getTimestamp();

    public void setTimestamp(long ts);

    public WindowModel getWindowOperator();

    public void setWindowOperator(WindowModel w);

}
