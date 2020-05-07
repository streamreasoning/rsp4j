package it.polimi.jasper.operators.r2s;

import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

import java.util.HashSet;
import java.util.Set;

public class JIStream<T> implements RelationToStreamOperator<T> {
    private final int i;
    private Set<SolutionMapping<T>> old_bindings = new HashSet<>();
    private Set<SolutionMapping<T>> new_bindings = new HashSet<>();
    private long ti_1 = -1;

    public JIStream(int i) {
        this.i = i;
    }

    @Override
    public T eval(SolutionMapping<T> new_response, long ts) {
        if (ti_1 < ts) {
            old_bindings.clear();
            old_bindings = new_bindings;
            new_bindings = new HashSet<>();
            ti_1 = ts;
        }

        new_bindings.add(new_response);

        if (!old_bindings.contains(new_response))
            return new_response.get();

        return null;
    }
}
