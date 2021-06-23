package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Rstream<R, O> implements RelationToStreamOperator<R, O> {

    public static <R, O> RelationToStreamOperator<R, O> get() {
        return new Rstream<R, O>();
    }


    //TODO instead of casting, the R2R could take a mapping function. An example could be for building output graphs
    @Override
    public O transform(R last_response, long ts) {
        return (O) last_response; //TODO add converter
    }
}