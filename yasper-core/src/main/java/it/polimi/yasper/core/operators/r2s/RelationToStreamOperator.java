package it.polimi.yasper.core.operators.r2s;

import it.polimi.yasper.core.querying.result.SolutionMapping;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator<T> {

    T eval(SolutionMapping<T> sm, long ts);

    default Collection<T> eval(Collection<SolutionMapping<T>> sml, long ts) {
        return sml.stream().map(sm -> eval(sm, ts)).collect(Collectors.toList());
    }
}
