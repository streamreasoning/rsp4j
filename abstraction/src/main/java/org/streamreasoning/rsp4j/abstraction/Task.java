package org.streamreasoning.rsp4j.abstraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;

import java.util.*;

public class Task<I,R,O> {
    private Set<S2RContainer> s2rs;
    private List<R2RContainer> r2rs;
    private Set<R2SContainer> r2ss;

    private Map<String,String> prefixes;


    public Task(TaskBuilder builder) {
        this.s2rs = builder.s2rs;
        this.r2rs = builder.r2rs;
        this.r2ss = builder.r2ss;
        this.prefixes = builder.prefixes;
    }


    public Set<S2RContainer> getS2Rs() {
        return s2rs;
    }

    public List<R2RContainer> getR2Rs() {
        return r2rs;
    }

    public Set<R2SContainer> getR2Ss() {
        return r2ss;
    }

    public static class TaskBuilder<I,R,O> {

        private Set<S2RContainer> s2rs;
        private List<R2RContainer> r2rs;
        private Set<R2SContainer> r2ss;

        private Map<String,String> prefixes;

        public TaskBuilder() {
            this.s2rs = new HashSet<>();
            this.r2rs = new ArrayList<>();
            this.r2ss = new HashSet<>();
            this.prefixes = new HashMap<String,String>();
        }

        public TaskBuilder prefix(String prefix, String url){
            prefixes.put(prefix,url);
            return this;
        }

        public TaskBuilder addS2R(String sourceURI, StreamToRelationOperatorFactory<I,R> s2rFactory, String tvgName) {
            s2rs.add(new S2RContainer(sourceURI, s2rFactory, tvgName));
            return this;
        }

        public TaskBuilder addR2R(String tvgName, RelationToRelationOperator<R> r2rFactory) {
            this.r2rs.add(new R2RContainer(tvgName, r2rFactory));
            return this;
        }

        public TaskBuilder addR2S(String sinkURI, RelationToStreamOperator<R> r2sFactory) {
            this.r2ss.add(new R2SContainer(sinkURI, r2sFactory));
            return this;
        }


        public Task build() {
            return new Task(this);
        }


    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class S2RContainer {
        @Getter
        private String sourceURI;
        @Getter
        private StreamToRelationOperatorFactory s2rFactory;
        @Getter
        private String tvgName;
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2RContainer {
        @Getter
        private String tvgName;
        @Getter
        private RelationToRelationOperator r2rFactory;

    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2SContainer {
        @Getter
        private String sinkURI;
        @Getter
        private RelationToStreamOperator r2rFactory;

    }
}
