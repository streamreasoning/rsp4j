package it.polimi.yasper.core.operators.r2r;

import it.polimi.yasper.core.querying.result.SolutionMapping;

import java.util.Collection;

public interface RelationToRelationOperator<T> {

    Collection<SolutionMapping<T>> eval(long ts);

}
