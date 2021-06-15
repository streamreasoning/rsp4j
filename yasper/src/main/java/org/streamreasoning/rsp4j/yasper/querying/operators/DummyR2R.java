package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class DummyR2R implements RelationToRelationOperator<Triple> {

    private final SDS<Graph> sds;
    private final ContinuousQuery query;
    private final Dataset ds;

    public DummyR2R(SDS<Graph> sds, ContinuousQuery query) {
        this.sds = sds;
        this.query = query;
        this.ds = (Dataset) sds;
    }

    @Override
    public Stream<Triple> eval(long ts) {
        return ds.stream()
                .map(Quad::asTriple);
    }

    @Override
    public TimeVarying<Collection<Triple>> apply() {
        return null;
    }
}
