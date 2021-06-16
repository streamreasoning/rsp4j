package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class R2RImpl implements RelationToRelationOperator<Triple> {

    private final SDS<Graph> sds;
    private final ContinuousQuery query;

    public R2RImpl(SDS<Graph> sds, ContinuousQuery query) {
        this.sds = sds;
        this.query = query;
    }

    @Override
    public Stream<Triple> eval() {
        return sds.toStream().flatMap(Graph::stream);

    }

    @Override
    public TimeVarying<Collection<Triple>> apply() {
        return null;
    }
}
