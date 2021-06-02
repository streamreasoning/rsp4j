package org.streamreasoning.rsp4j.abstraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;

import java.util.*;

public class Task<I, R, O> {
    private final Set<S2RContainer<I, R>> s2rs;
    private final List<R2RContainer<R>> r2rs;
    private final Set<R2SContainer<O>> r2ss;


    public Task(TaskBuilder<I, R, O> builder) {
        this.s2rs = builder.s2rs;
        this.r2rs = builder.r2rs;
        this.r2ss = builder.r2ss;
    }


    public Set<S2RContainer<I, R>> getS2Rs() {
        return s2rs;
    }

    public List<R2RContainer<R>> getR2Rs() {
        return r2rs;
    }

    public Set<R2SContainer<O>> getR2Ss() {
        return r2ss;
    }

    public static class TaskBuilder<I, R, O> {

        private Set<S2RContainer<I, R>> s2rs;
        private List<R2RContainer<R>> r2rs;
        private Set<R2SContainer<O>> r2ss;

        private Map<String, String> prefixes;

        public TaskBuilder() {
            this.s2rs = new HashSet<>();
            this.r2rs = new ArrayList<>();
            this.r2ss = new HashSet<>();
            this.prefixes = new HashMap<String, String>();
        }

        public TaskBuilder<I, R, O> prefix(String prefix, String url) {
            prefixes.put(prefix, url);
            return this;
        }

        public TaskBuilder<I, R, O> addS2R(String sourceURI, StreamToRelationOp<I, R> s2rFactory, String tvgName) {
            s2rs.add(new S2RContainer<I, R>(sourceURI, s2rFactory, tvgName));
            return this;
        }

        public TaskBuilder<I, R, O> addR2R(String tvgName, RelationToRelationOperator<R> r2rFactory) {
            this.r2rs.add(new R2RContainer<R>(tvgName, r2rFactory));
            return this;
        }

        public TaskBuilder<I, R, O> addR2S(String sinkURI, RelationToStreamOperator<O> r2sFactory) {
            this.r2ss.add(new R2SContainer<O>(sinkURI, r2sFactory));
            return this;
        }


        public Task build() {
            return new Task(this);
        }


    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class S2RContainer<I, R> {
        @Getter
        private String sourceURI;
        @Getter
        private StreamToRelationOp<I, R> s2r;
        @Getter
        private String tvgName;
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2RContainer<R> {
        @Getter
        private String tvgName;
        @Getter
        private RelationToRelationOperator<R> r2rFactory;

    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2SContainer<O> {
        @Getter
        private String sinkURI;
        @Getter
        private RelationToStreamOperator<O> r2rFactory;

    }
}
