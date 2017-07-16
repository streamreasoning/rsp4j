package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EventBean;
import it.polimi.yasper.core.enums.Maintenance;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;

/**
 * Created by riccardo on 12/07/2017.
 */
public interface WindowOperator {

    void addListener(TimeVaryingGraph tvg);

    EventBean[] getWindowContent(long t0);

    String getName();

    String getText();


    void DStreamUpdate(InstantaneousItem graph, EventBean[] oldData, Maintenance maintenance);

    void IStreamUpdate(InstantaneousItem graph, EventBean[] newData);

    long getT0();
    long getRange();
    long getStep();
}
