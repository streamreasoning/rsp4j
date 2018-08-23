package it.polimi.yasper.core.rspql.operators.r2s;

import it.polimi.yasper.core.rspql.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    InstantaneousResponse eval(InstantaneousResponse last_response);
}
