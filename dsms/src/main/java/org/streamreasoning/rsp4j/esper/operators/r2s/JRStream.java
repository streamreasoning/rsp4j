package org.streamreasoning.rsp4j.esper.operators.r2s;


import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.stream.Stream;

public class JRStream<R> implements RelationToStreamOperator<SolutionMapping<R>,SolutionMapping<R>> {

    @Override
    public Stream<SolutionMapping<R>> eval(Stream<SolutionMapping<R>> last_response, long ts) {
        return last_response;
    }
}