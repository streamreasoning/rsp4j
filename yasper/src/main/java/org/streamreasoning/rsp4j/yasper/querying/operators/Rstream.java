package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

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