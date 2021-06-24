package org.streamreasoning.rsp4j.abstraction.utils;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class R2RPipe<W, R> implements RelationToRelationOperator<W, R> {

    private RelationToRelationOperator[] r2rs;

    public R2RPipe(RelationToRelationOperator... r2rs) {
        this.r2rs = r2rs;
    }

    @Override
    public Stream<R> eval(Stream<W> sds) {
        Stream tvg = sds;
        for (RelationToRelationOperator r2r : r2rs) {
            tvg = r2r.eval(tvg);
        }
        return tvg;
    }

    @Override
    public TimeVarying<Collection<R>> apply(SDS<W> sds) {
        return null;
    }

    @Override
    public SolutionMapping<R> createSolutionMapping(R result) {
        return null;
    }
}
