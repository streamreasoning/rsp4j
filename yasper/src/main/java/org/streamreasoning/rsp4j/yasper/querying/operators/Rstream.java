package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Rstream<R,O> implements RelationToStreamOperator<R,O> {

    public static RelationToStreamOperator get() {
        return new Rstream();
    }

    @Override
    public O eval(SolutionMapping<R> last_response, long ts) {
        return (O)last_response.get(); //TODO add converter
    }
}