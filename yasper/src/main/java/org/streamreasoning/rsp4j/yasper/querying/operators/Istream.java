package org.streamreasoning.rsp4j.yasper.querying.operators;

import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Istream<R, O> implements RelationToStreamOperator<R, O> {
    private final int i;
    private Collection<R> last_responses;

    public Istream(int i) {
        this.i = i;
    }

    public static <R, O> RelationToStreamOperator<R, O> get() {
        return new Istream<>(1);
    }

//    @Override
//    public O transform(R new_response, long ts) {
//        if (last_response == null)
//            last_response = new_response;
//        O difference = new_response.difference(last_response);
//        last_response = new_response;
//        return difference;//TODO add converter
//    }

    @Override
    public Stream<O> eval(Stream<R> sml, long ts) {
        Collection<R> current = sml.collect(Collectors.toList());
        if (last_responses == null)
            last_responses = current;
        Stream<O> oStream = current.stream().filter(r -> !last_responses.contains(r)).map(r -> transform(r, ts));
        last_responses = current;
        return oStream;
    }

}

