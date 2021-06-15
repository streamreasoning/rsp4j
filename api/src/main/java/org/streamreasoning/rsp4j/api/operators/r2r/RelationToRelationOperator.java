package org.streamreasoning.rsp4j.api.operators.r2r;


import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public interface RelationToRelationOperator<T> {

    //TODO this should not be time-aware
    Stream<T> eval(long ts);

    TimeVarying<Collection<T>> apply();

}
