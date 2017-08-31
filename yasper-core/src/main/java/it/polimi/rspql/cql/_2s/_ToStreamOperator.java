package it.polimi.rspql.cql._2s;

import it.polimi.yasper.core.query.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface _ToStreamOperator {

    InstantaneousResponse eval(InstantaneousResponse last_response);
}
