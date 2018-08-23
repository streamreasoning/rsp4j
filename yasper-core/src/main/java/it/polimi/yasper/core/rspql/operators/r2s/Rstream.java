package it.polimi.yasper.core.rspql.operators.r2s;

import it.polimi.yasper.core.rspql.response.InstantaneousResponse;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Rstream implements RelationToStreamOperator {

    public static RelationToStreamOperator get() {
        return new Rstream();
    }

    @Override
    public InstantaneousResponse eval(InstantaneousResponse last_response) {
        return last_response;
    }
}