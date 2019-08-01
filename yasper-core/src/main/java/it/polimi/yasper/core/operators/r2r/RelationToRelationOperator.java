package it.polimi.yasper.core.operators.r2r;

import it.polimi.yasper.core.querying.result.SolutionMapping;

import java.util.stream.Stream;

public interface RelationToRelationOperator<T> {

    Stream<SolutionMapping<T>> eval(long ts);

}
