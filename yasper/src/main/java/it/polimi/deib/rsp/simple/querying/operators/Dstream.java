package it.polimi.deib.rsp.simple.querying.operators;

import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Dstream<I> implements RelationToStreamOperator<I> {
    private final int i;
    private SolutionMapping<I> last_response;

    public Dstream(int i) {
        this.i = i;
    }

    public static RelationToStreamOperator get() {
        return new Dstream(1);
    }

    @Override
    public I eval(SolutionMapping<I> new_response, long ts) {
        SolutionMapping<I> diff = last_response.difference(new_response);
        last_response = new_response;
        return diff.get();
    }

}