package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.SelectInstResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class R2RImpl implements RelationToRelationOperator<Graph, Triple> {

    private final SDS<Graph> sds;
    private final ContinuousQuery query;
    private final List<Triple> solutions = new ArrayList<>();

    public R2RImpl(SDS<Graph> sds, ContinuousQuery query) {
        this.sds = sds;
        this.query = query;
    }

    @Override
    public Stream<Triple> eval(Stream<Graph> sds) {
        return this.sds.toStream().flatMap(Graph::stream);

    }


    @Override
    public TimeVarying<Collection<Triple>> apply(SDS<Graph> sds) {
        return new TimeVarying<Collection<Triple>>() {
            @Override
            public void materialize(long ts) {
                List<Triple> collect = eval(sds.toStream()).collect(Collectors.toList());
                solutions.clear();
                solutions.addAll(collect);
            }

            @Override
            public Collection<Triple> get() {
                return solutions;
            }

            @Override
            public String iri() {
                return null;
            }
        };
    }

    @Override
    public SolutionMapping<Triple> createSolutionMapping(Triple result) {
        return new SelectInstResponse<Triple>(query.getID() + "/ans/" + System.currentTimeMillis(), System.currentTimeMillis(),result);

    }
}
