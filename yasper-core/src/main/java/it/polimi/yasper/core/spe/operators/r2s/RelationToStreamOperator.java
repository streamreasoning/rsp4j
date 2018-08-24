package it.polimi.yasper.core.spe.operators.r2s;

import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    InstantaneousResult eval(InstantaneousResult last_response);
}
