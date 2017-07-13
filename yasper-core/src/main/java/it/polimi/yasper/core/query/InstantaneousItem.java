package it.polimi.yasper.core.query;

import it.polimi.yasper.core.timevarying.TimeVaryingGraph;

/**
 * Created by riccardo on 05/07/2017.
 */
public interface InstantaneousItem {

    public long getTimestamp();

    public void setTimestamp(long ts);

    public TimeVaryingGraph getWindowOperator();

    public void setWindowOperator(TimeVaryingGraph w);

    public void clear();

    public void addContent(Object o);

    public void removeContent(Object o);
}
