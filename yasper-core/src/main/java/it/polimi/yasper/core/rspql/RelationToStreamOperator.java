package it.polimi.yasper.core.rspql;

import it.polimi.yasper.core.query.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    InstantaneousResponse eval(InstantaneousResponse last_response);
}
