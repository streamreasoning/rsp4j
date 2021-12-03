package org.streamreasoning.rsp4j.yasper.querying.operators.r2r;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.JoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.NestedJoinAlgorithm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 * A simple Basic Graph Pattern (BGP) implementation consisting of multiple Triple Patterns (TP).
 */
public class BGP implements RelationToRelationOperator<Graph, Binding> {
    private List<TP> tps;
    private JoinAlgorithm<Binding> joinAlgorithm;
    private BGP(){
        tps = new ArrayList<>();
        joinAlgorithm = new NestedJoinAlgorithm();
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
            allBindings = joinAlgorithm.join(allBindings,tpEval);
        }
        return allBindings.stream();
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

    public void setJoinAlgorithm(JoinAlgorithm<Binding> joinAlgorithm){
        this.joinAlgorithm = joinAlgorithm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BGP bgp = (BGP) o;
        return Objects.equals(tps, bgp.tps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tps);
    }

    public List<TP> getTPs() {
        return tps;
    }
}
