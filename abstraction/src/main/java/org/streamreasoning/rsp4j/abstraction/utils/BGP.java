package org.streamreasoning.rsp4j.abstraction.utils;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 * A simple Basic Graph Pattern (BGP) implementation consisting of multiple Triple Patterns (TP).
 */
public class BGP implements RelationToRelationOperator<Graph, Binding> {
    private List<TP> tps;

    private BGP(){
        tps = new ArrayList<>();
    }
    private BGP(TP tp1){
        this();
        this.tps.add(tp1);
    }
    public static BGP createFrom(TP tp1) {
        return new BGP(tp1);
    }

    @Override
    public Stream<Binding> eval(Stream<Graph> sds) {
        TP tpPrev = tps.get(0);
        Set<Graph> graphSet = sds.collect(Collectors.toSet());
        Set<Binding> allBindings = tpPrev.eval(graphSet.stream()).collect(Collectors.toSet());
        for(int i = 1 ; i < tps.size(); i ++){
            Set<Binding> tpEval = tps.get(i).eval(graphSet.stream()).collect(Collectors.toSet());
            allBindings = join(allBindings,tpEval);
        }
        return allBindings.stream();
    }
    private Set<Binding> join(Set<Binding> bindings1, Set<Binding> bindings2){
        Set<Binding> results = new HashSet<>();
        for(Binding b1 : bindings1){
            for(Binding b2: bindings2){
                joinBinding(b1,b2)
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
    @Override
    public TimeVarying<Collection<Binding>> apply(SDS<Graph> sds) {
        return null;
    }

    @Override
    public SolutionMapping<Binding> createSolutionMapping(Binding result) {
        return null;
    }

    public BGP join(TP tp) {
        tps.add(tp);
        return this;
    }

    public BGP create() {
        return this;
    }
}
