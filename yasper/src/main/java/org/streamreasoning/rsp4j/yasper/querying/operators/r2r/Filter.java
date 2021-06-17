package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter<T> implements RelationToRelationOperator<T, T>, Function<T, T> {

    private Stream<T> solutions;
    private Predicate<T> p;
    private Collection<T> solutions2;

    public Filter(Stream<T> solutions, Predicate<T> p) {
        this.solutions = solutions;
        this.p = p;
        solutions2 = new ArrayList<>();
    }

    public Filter(TimeVarying<Collection<T>> tvlo, Predicate<T> p) {
        this.solutions2 = tvlo.get();
        this.p = p;
    }

    @Override
    public Stream<T> eval(Stream<T> sds) {
        return sds.filter(p);
    }

    @Override
    public TimeVarying<Collection<T>> apply(SDS<T> sds) {
        //TODO this should return an SDS<O>
        return new TimeVarying<Collection<T>>() {
            @Override
            public void materialize(long ts) {
                Stream<T> sds1;
                if (solutions != null)
                    sds1 = solutions
                            .filter(Objects::nonNull);
                else sds1 = solutions2.stream()
                        .filter(Objects::nonNull);
                List<T> collect = eval(sds1).collect(Collectors.toList());
                solutions2.clear();
                solutions2.addAll(collect);
            }

            @Override
            public Collection<T> get() {
                return solutions2;
            }

            @Override
            public String iri() {
                return null;
            }
        };
    }

    @Override
    public T apply(T t) {
        return p.test(t) ? t : null;
    }

}
