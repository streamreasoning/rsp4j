package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BGP implements RelationToRelationOperator<Binding> {

    private final SDS<Graph> sds;
    private final VarOrTerm s;
    private final VarOrTerm p;
    private final VarOrTerm o;

    private List<RelationToRelationOperator<Binding>> ops = new ArrayList<>();

    public BGP(SDS<Graph> sds, VarOrTerm s, VarOrTerm p, VarOrTerm o) {
        this.sds = sds;
        this.s = s;
        this.p = p;
        this.o = o;
    }

    @Override
    //it returns a stream of variable bindings, which is a sequence
    public Stream<Binding> eval() {
        return sds.toStream().flatMap(Graph::stream)
                .map(t -> {
                    Binding b = new BindingImpl();

                    boolean sb = this.s.bind(b, t.getSubject());

                    boolean ob = o.bind(b, t.getObject());

                    boolean pb = p.bind(b, t.getPredicate());

                    if (!sb || !ob || !pb) {
                        return null;
                    }

                    return b;
                }).filter(Objects::nonNull);
    }

    private List<Binding> solutions = new ArrayList<>();

    @Override
    public TimeVarying<Collection<Binding>> apply() {

        return new TimeVarying<Collection<Binding>>() {
            @Override
            public void materialize(long ts) {
                //time should not be important
                solutions.clear();
                solutions.addAll(eval().collect(Collectors.toList()));
            }

            @Override
            public Collection<Binding> get() {
                return solutions;
            }

            @Override
            public String iri() {
                return null;
            }
        };
    }

}
