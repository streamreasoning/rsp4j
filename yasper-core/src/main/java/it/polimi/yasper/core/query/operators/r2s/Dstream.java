package it.polimi.yasper.core.query.operators.r2s;

import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.rspql.cql._2s._ToStreamOperator;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Dstream implements _ToStreamOperator {
    private final int i;
    private InstantaneousResponse last_response;

    public Dstream(int i) {
        this.i = i;
    }

    public static _ToStreamOperator get() {
        return new Dstream(1);
    }

    @Override
    public InstantaneousResponse eval(InstantaneousResponse new_response) {
        InstantaneousResponse diff = last_response.difference(new_response);
        last_response = new_response;
        return diff;
    }
}