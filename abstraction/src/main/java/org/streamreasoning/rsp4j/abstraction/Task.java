package org.streamreasoning.rsp4j.abstraction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.table.TableRowStream;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;

import java.util.*;

public class Task<I, W, R, O> {

    private final Set<S2RContainer<I, W>> s2rs;
    private final List<R2RContainer<W, R>> r2rs;
    private final Set<R2SContainer<R,O>> r2ss;
    private List<AggregationContainer> aggregations;
    private Map<String, String> prefixes;

    public Task(TaskBuilder<I, W, R, O> builder) {
        this.s2rs = builder.s2rs;
        this.r2rs = builder.r2rs;
        this.r2ss = builder.r2ss;
        this.prefixes = builder.prefixes;
        this.aggregations = builder.aggregations;
    }

    public Set<S2RContainer<I, W>> getS2Rs() {
        return s2rs;
    }

    public List<R2RContainer<W, R>> getR2Rs() {
        return r2rs;
    }

    public Set<R2SContainer<R,O>> getR2Ss() {
        return r2ss;
    }

    public List<AggregationContainer> getAggregations() {
        return aggregations;
    }

    public static class TaskBuilder<I, W , R, O> {

        private Set<S2RContainer<I, W>> s2rs;
        private List<R2RContainer<W, R>> r2rs;
        private Set<R2SContainer<R,O>> r2ss;
        private List<AggregationContainer> aggregations;
        private Map<String, String> prefixes;

        public TaskBuilder() {
            this.s2rs = new HashSet<>();
            this.r2rs = new ArrayList<>();
            this.r2ss = new HashSet<>();
            this.prefixes = new HashMap<String, String>();
            this.aggregations = new ArrayList<>();
        }

        public TaskBuilder<I, W , R, O> prefix(String prefix, String url) {
            prefixes.put(prefix, url);
            return this;
        }

        public TaskBuilder<I, W , R, O> addS2R(String sourceURI, StreamToRelationOp<I, W> s2r, String tvgName) {
            s2rs.add(new S2RContainer<I, W>(sourceURI, s2r, tvgName));
            return this;
        }

        public TaskBuilder<I, W , R, O> addR2R(String tvgName, RelationToRelationOperator<W, R> r2r) {
            r2rs.add(new R2RContainer<W, R>(tvgName, r2r));
            return this;
        }

        public TaskBuilder<I, W , R, O> addR2S(String sinkURI, RelationToStreamOperator<R,O> r2s) {
            r2ss.add(new R2SContainer<R,O>(sinkURI, r2s));
            return this;
        }

        public TaskBuilder<I, W , R, O> aggregate(String tvgName, String functionName, String inputVariable, String outputVariable) {
            aggregations.add(new AggregationContainer(tvgName, functionName, inputVariable, outputVariable));
            return this;
        }


        public Task<I, W , R, O> build() {
            return new Task<I, W , R, O>(this);
        }


    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class S2RContainer<I, W> {
        @Getter
        private String sourceURI;
        @Getter
        private StreamToRelationOp<I, W> s2rOperator;
        @Getter
        private String tvgName;
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2RContainer<W,R> {
        @Getter
        private String tvgName;
        @Getter
        private RelationToRelationOperator<W, R> r2rOperator;

    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class R2SContainer<R, O> {
        @Getter
        private String sinkURI;
        @Getter
        private RelationToStreamOperator<R, O> r2sOperator;

    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class AggregationContainer<R> {
        @Getter
        private String tvgName;
        @Getter
        private String functionName;
        @Getter
        private String inputVariable;
        @Getter
        private String outputVariable;

    }
}
