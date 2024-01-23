package org.streamreasoning.rsp4j.esper.operators.r2s;



import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JDStream<R> implements RelationToStreamOperator<SolutionMapping<R>,SolutionMapping<R>> {
    private final int i;
    private Set<SolutionMapping<R>> old_bindings = new HashSet<>();
    private Set<SolutionMapping<R>> new_bindings = new HashSet<>();
    private long ti_1 = -1;

    public JDStream(int i) {
        this.i = i;
    }

    @Override
    public Stream<SolutionMapping<R>> eval(Stream<SolutionMapping<R>> new_response, long ts) {
        if (ti_1 < ts) {
            ti_1 = ts;
            old_bindings.clear();
            old_bindings = new_bindings;
            new_bindings = new HashSet<>();
        }
        List<SolutionMapping<R>> new_input = new_response.collect(Collectors.toList());
        new_bindings.addAll(new_input);

        if (old_bindings.containsAll(new_input))
            return new_input.stream();

        return null;
    }
}