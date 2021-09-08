package org.streamreasoning.rsp4j.examples.operators.r2r;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SimpleR2RFilter<T> implements RelationToRelationOperator<T, T> {

    private final Predicate<T> p;

    public SimpleR2RFilter(Predicate<T> p){
        this.p = p;
    }
    @Override
    public Stream<T> eval(Stream<T> sds) {
        return sds.filter(p);
    }

    @Override
    public TimeVarying<Collection<T>> apply(SDS<T> sds) {
        return null;
    }

    @Override
    public SolutionMapping<T> createSolutionMapping(T result) {
        return null;
    }
}
