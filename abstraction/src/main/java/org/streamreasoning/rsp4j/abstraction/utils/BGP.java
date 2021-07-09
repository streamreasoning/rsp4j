package org.streamreasoning.rsp4j.abstraction.utils;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.content.ContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BGP implements RelationToRelationOperator<Graph, Binding> {
    private List<TP> tps;
    private Map<TP, Var> joinVars;
    private BGP(TP tp1){
        tps = new ArrayList<>();
        joinVars = new HashMap<>();
        this.tps.add(tp1);
    }
    public static BGP createFrom(TP tp1) {
        return new BGP(tp1);
    }

    @Override
    public Stream<Binding> eval(Stream<Graph> sds) {
        Set<Binding> allBindings = new HashSet<>();
        TP tpPrev = tps.get(0);
        Set<Graph> graphSet = sds.collect(Collectors.toSet());
        allBindings = tpPrev.eval(graphSet.stream()).collect(Collectors.toSet());
        for(int i = 1 ; i < tps.size(); i ++){
            Set<Binding> tpEval = tps.get(i).eval(graphSet.stream()).collect(Collectors.toSet());
            allBindings = join(allBindings,tpEval,joinVars.get(tps.get(i)));
        }
        return allBindings.stream();
    }
    private Set<Binding> join(Set<Binding> bindings1, Set<Binding> bindings2, Var joinVar){
        Set<Binding> results = new HashSet<>();
        for(Binding b1 : bindings1){
            for(Binding b2: bindings2){
                Optional<Binding> joinBinding = joinBindings(b1,b2,joinVar);
                if(joinBinding.isPresent()){
                    results.add(joinBinding.get());
                }
            }
        }
        return results;
    }
    private Optional<Binding> joinBindings(Binding b1, Binding b2, Var joinVar){
        if(b1.variables().contains(joinVar) && b1.value(joinVar).equals(b2.value(joinVar))){
            return Optional.of(b1.union(b2));
        }else{
            return Optional.empty();
        }
    }
    @Override
    public TimeVarying<Collection<Binding>> apply(SDS<Graph> sds) {
        return null;
    }

    @Override
    public SolutionMapping<Binding> createSolutionMapping(Binding result) {
        return null;
    }

    public BGP joinOn(TP tp, VarOrTerm var) {
        tps.add(tp);
        joinVars.put(tp,var);
        return this;
    }

    public BGP create() {
        return this;
    }
}
