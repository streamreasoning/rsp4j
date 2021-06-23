package org.streamreasoning.rsp4j.api.operators.r2s;

import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator<R, O> {

    default O transform(R sm, long ts) {
        return (O) sm;
    }

    default Stream<O> eval(Stream<R> sml, long ts) {
        return sml.map(e -> transform(e, ts));
    }

    default Collection<O> eval(TimeVarying<Collection<R>> sml, long ts) {
        sml.materialize(ts);
        Collection<R> rs = sml.get();
        return eval(rs.stream(), ts).collect(Collectors.toList());
    }
}