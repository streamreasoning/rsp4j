package it.polimi.yasper.core.quering.operators.r2s;

import it.polimi.yasper.core.quering.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    InstantaneousResponse eval(InstantaneousResponse last_response);
}
