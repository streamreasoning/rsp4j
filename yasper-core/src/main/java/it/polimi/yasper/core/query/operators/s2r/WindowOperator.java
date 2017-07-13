package it.polimi.yasper.core.query.operators.s2r;

import it.polimi.yasper.core.timevarying.TimeVaryingGraph;

/**
 * Created by riccardo on 12/07/2017.
 */
public interface WindowOperator {

    void addListener(TimeVaryingGraph defaultTVG);

    String getName();

    String getText();
}
