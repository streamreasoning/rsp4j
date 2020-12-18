package it.polimi.deib.rsp.simple.querying.operators;

import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Rstream<T> implements RelationToStreamOperator<T> {

    public static RelationToStreamOperator get() {
        return new Rstream();
    }

    @Override
    public T eval(SolutionMapping<T> last_response, long ts) {
        return last_response.get();
    }
}