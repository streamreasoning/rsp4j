package it.polimi.yasper.core.spe.operators.r2s;

import it.polimi.yasper.core.spe.operators.r2s.result.InstantaneousResult;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Rstream implements RelationToStreamOperator {

    public static RelationToStreamOperator get() {
        return new Rstream();
    }

    @Override
    public InstantaneousResult eval(InstantaneousResult last_response) {
        return last_response;
    }
}