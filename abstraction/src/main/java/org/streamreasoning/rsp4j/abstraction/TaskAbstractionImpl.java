package org.streamreasoning.rsp4j.abstraction;

import org.streamreasoning.rsp4j.abstraction.containers.AggregationContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2RContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2SContainer;
import org.streamreasoning.rsp4j.abstraction.containers.S2RContainer;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;

import java.util.*;

public class TaskAbstractionImpl<I, W, R, O> implements Task<I, W, R, O> {

    private final Set<S2RContainer<I, W>> s2rs;
    private final List<R2RContainer<W, R>> r2rs;
    private final Set<R2SContainer<R, O>> r2ss;
    private List<AggregationContainer> aggregations;
    private Map<String, String> prefixes;

    public TaskAbstractionImpl(TaskBuilder<I, W, R, O> builder) {
        this.s2rs = builder.s2rs;
        this.r2rs = builder.r2rs;
        this.r2ss = builder.r2ss;
        this.prefixes = builder.prefixes;
        this.aggregations = builder.aggregations;
    }

    @Override
    public Set<S2RContainer<I, W>> getS2Rs() {
        return s2rs;
    }

    @Override
    public List<R2RContainer<W, R>> getR2Rs() {
        return r2rs;
    }

    @Override
    public Set<R2SContainer<R, O>> getR2Ss() {
        return r2ss;
    }

    @Override
    public List<AggregationContainer> getAggregations() {
        return aggregations;
    }


    public static class TaskBuilder<I, W, R, O> {

        private Set<S2RContainer<I, W>> s2rs;
        private List<R2RContainer<W, R>> r2rs;
        private Set<R2SContainer<R, O>> r2ss;
        private List<AggregationContainer> aggregations;
        private Map<String, String> prefixes;

        public TaskBuilder() {
            this.s2rs = new HashSet<>();
            this.r2rs = new ArrayList<>();
            this.r2ss = new HashSet<>();
            this.prefixes = new HashMap<String, String>();
            this.aggregations = new ArrayList<>();
        }

        public TaskBuilder<I, W, R, O> prefix(String prefix, String url) {
            prefixes.put(prefix, url);
            return this;
        }

        public TaskBuilder<I, W, R, O> addS2R(String sourceURI, StreamToRelationOp<I, W> s2r, String tvgName) {
            s2rs.add(new S2RContainer<I, W>(sourceURI, s2r, tvgName));
            return this;
        }

        public TaskBuilder<I, W, R, O> addR2R(String tvgName, RelationToRelationOperator<W, R> r2r) {
            r2rs.add(new R2RContainer<W, R>(tvgName, r2r));
            return this;
        }

        public TaskBuilder<I, W, R, O> addR2S(String sinkURI, RelationToStreamOperator<R, O> r2s) {
            r2ss.add(new R2SContainer<R, O>(sinkURI, r2s));
            return this;
        }

        public TaskBuilder<I, W, R, O> aggregate(String tvgName, String functionName, String inputVariable, String outputVariable) {
            aggregations.add(new AggregationContainer(tvgName, functionName.toUpperCase(), inputVariable, outputVariable));
            return this;
        }


        public TaskAbstractionImpl<I, W, R, O> build() {
            return new TaskAbstractionImpl<I, W, R, O>(this);
        }


    }


}
