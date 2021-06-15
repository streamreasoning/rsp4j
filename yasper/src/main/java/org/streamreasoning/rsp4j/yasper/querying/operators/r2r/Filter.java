package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Filter<T> implements RelationToRelationOperator<T>, Function<T, T> {

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
    public Stream<T> eval(long ts) {
        if (solutions != null)
            return solutions
                    .filter(Objects::nonNull)
                    .filter(p);
        else return solutions2.stream()
                .filter(Objects::nonNull)
                .filter(p);
    }

    @Override
    public TimeVarying<Collection<T>> apply() {
        return new TimeVarying<Collection<T>>() {
            @Override
            public void materialize(long ts) {
                List<T> collect = eval(ts).collect(Collectors.toList());
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
