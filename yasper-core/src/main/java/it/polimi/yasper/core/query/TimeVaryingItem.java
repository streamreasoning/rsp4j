package it.polimi.yasper.core.query;

import it.polimi.yasper.core.query.operators.s2r.WindowOperator;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface TimeVaryingItem {

    public long getTimestamp();

    public void setTimestamp(long ts);

    public WindowOperator getWindowOperator();

    public void setWindowOperator(WindowOperator w);

    public void clear();

}
