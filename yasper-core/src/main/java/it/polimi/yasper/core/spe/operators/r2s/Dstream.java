package it.polimi.yasper.core.spe.operators.r2s;

import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Dstream implements RelationToStreamOperator {
    private final int i;
    private InstantaneousResult last_response;

    public Dstream(int i) {
        this.i = i;
    }

    public static RelationToStreamOperator get() {
        return new Dstream(1);
    }

    @Override
    public InstantaneousResult eval(InstantaneousResult new_response) {
        InstantaneousResult diff = last_response.difference(new_response);
        last_response = new_response;
        return diff;
    }
}