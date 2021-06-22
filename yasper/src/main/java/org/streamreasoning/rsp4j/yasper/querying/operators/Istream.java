package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Istream<R,O> implements RelationToStreamOperator<R,O> {
    private final int i;
    private SolutionMapping<R> last_response;

    public Istream(int i) {
        this.i = i;
    }

    public static RelationToStreamOperator get() {
        return new Istream(1);
    }

    @Override
    public O eval(SolutionMapping<R> new_response, long ts) {
        if (last_response == null) {
            last_response = new_response;
            return (O)last_response.get(); //TODO add converter
        } else {
            SolutionMapping<R> diff = new_response.difference(last_response);
            last_response = new_response;
            return (O) diff.get();//TODO add converter
        }
    }

}
