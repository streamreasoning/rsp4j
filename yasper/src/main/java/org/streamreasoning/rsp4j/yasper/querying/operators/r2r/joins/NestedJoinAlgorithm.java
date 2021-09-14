package org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins;

import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.JoinAlgorithm;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/***
 * Implements the nested join algorithm
 */
public class NestedJoinAlgorithm implements JoinAlgorithm<Binding> {


    public Set<Binding> join(Set<Binding> bindings1, Set<Binding> bindings2) {
        Set<Binding> results = new HashSet<Binding>();
        for (Binding b1 : bindings1) {
            for (Binding b2 : bindings2) {
                joinBinding(b1, b2)
                        .ifPresent(r -> results.add(r));

            }
        }
        return results;
    }
    private Optional<Binding> joinBinding(Binding b1, Binding b2){
        //check that same variables are bound to same values
        Set<Var> overlappingVars = new HashSet<>(b1.variables());
        overlappingVars.retainAll(b2.variables());
        boolean succesfulljoin = overlappingVars.stream().map(v -> b1.value(v).equals(b2.value(v)))
                .allMatch(v -> v);
        if(succesfulljoin){
            return Optional.of(b1.union(b2));
        }
        return Optional.empty();

    }
}