package it.polimi.yasper.core.spe.operators.r2s;

import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Istream implements RelationToStreamOperator {
    private final int i;
    private InstantaneousResult last_response;

    public Istream(int i) {
        this.i = i;
    }

    public static RelationToStreamOperator get() {
        return new Istream(1);
    }

    @Override
    public InstantaneousResult eval(InstantaneousResult new_response) {
        if (last_response == null) {
            return last_response = new_response;
        } else {
            InstantaneousResult diff = new_response.difference(last_response);
            last_response = new_response;
            return diff;
        }
    }
}
