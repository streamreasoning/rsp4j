package it.polimi.deib.sr.rsp.api.operators.r2r;

import it.polimi.deib.sr.rsp.api.querying.result.SolutionMapping;

import java.util.stream.Stream;

public interface RelationToRelationOperator<T> {

    Stream<SolutionMapping<T>> eval(long ts);

}
