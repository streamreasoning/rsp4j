package org.streamreasoning.rsp4j.operatorapi;

import org.streamreasoning.rsp4j.operatorapi.containers.AggregationContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.R2RContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.R2SContainer;
import org.streamreasoning.rsp4j.operatorapi.containers.S2RContainer;
import org.streamreasoning.rsp4j.operatorapi.monitoring.MonitoringR2RProxy;
import org.streamreasoning.rsp4j.operatorapi.monitoring.MonitoringR2SProxy;
import org.streamreasoning.rsp4j.operatorapi.monitoring.MonitoringS2RProxy;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.yasper.querying.PrefixMap;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.util.*;
import java.util.stream.Collectors;

public class TaskOperatorAPIImpl<I, W, R, O> implements Task<I, W, R, O> {

    private final Set<S2RContainer<I, W>> s2rs;
    private final List<R2RContainer<W, R>> r2rs;
    private final Set<R2SContainer<R, O>> r2ss;
    private final DataSet<W> defaultGraph;
    private final List<Var> projection;
    private List<AggregationContainer> aggregations;
    private PrefixMap prefixes;

    public TaskOperatorAPIImpl(TaskBuilder<I, W, R, O> builder) {
        this.s2rs = builder.s2rs;
        this.r2rs = builder.r2rs;
        this.r2ss = builder.r2ss;
        this.prefixes = builder.prefixes;
        this.aggregations = builder.aggregations;
        this.defaultGraph = builder.defaultGraph;
        this.projection = builder.projection;

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

    @Override
    public DataSet<W> getDefaultGraph(){
        return defaultGraph;
    }

    @Override
    public List<Var> getProjection() {
        return projection!=null? projection : Collections.emptyList();
    }


    public static class TaskBuilder<I, W, R, O> {

        private Set<S2RContainer<I, W>> s2rs;
        private List<R2RContainer<W, R>> r2rs;
        private Set<R2SContainer<R, O>> r2ss;
        private List<AggregationContainer> aggregations;
        private PrefixMap prefixes;
        private DataSet<W> defaultGraph;
        private List<Var> projection;

        public TaskBuilder() {
            this.s2rs = new HashSet<>();
            this.r2rs = new ArrayList<>();
            this.r2ss = new HashSet<>();
            this.prefixes = new PrefixMap();
            this.aggregations = new ArrayList<>();
        }
        public TaskBuilder(PrefixMap prefixes) {
            this();
            this.prefixes = prefixes;
        }

        public TaskBuilder<I, W, R, O> addS2R(String sourceURI, StreamToRelationOp<I, W> s2r, String tvgName) {
            s2rs.add(new S2RContainer<I, W>(prefixes.expandIfPrefixed(sourceURI), s2r, tvgName));
            return this;
        }

        public TaskBuilder<I, W, R, O> addR2R(String tvgName, RelationToRelationOperator<W, R> r2r) {
            r2rs.add(new R2RContainer<W, R>(prefixes.expandIfPrefixed(tvgName), r2r));
            return this;
        }
        public TaskBuilder<I, W, R, O> addR2R(List<String> tvgNames, RelationToRelationOperator<W, R> r2r) {
            tvgNames = tvgNames.stream().map(tvg -> prefixes.expandIfPrefixed(tvg)).collect(Collectors.toList());
            r2rs.add(new R2RContainer<W, R>(tvgNames, r2r));
            return this;
        }

        public TaskBuilder<I, W, R, O> addR2S(String sinkURI, RelationToStreamOperator<R, O> r2s) {
            r2ss.add(new R2SContainer<R, O>(prefixes.expandIfPrefixed(sinkURI), r2s));
            return this;
        }
        public TaskBuilder<I, W, R, O> addDefaultGraph(DataSet<W> defaultGraph) {
            this.defaultGraph = defaultGraph;
            return this;
        }
        public TaskBuilder<I, W, R, O> addProjection(List<Var> projection) {
            this.projection = projection;
            return this;
        }
        public TaskBuilder<I, W, R, O> addProjectionStrings(List<String> projection) {
            this.projection = projection.stream().map(p-> new VarImpl(p)).collect(Collectors.toList());
            return this;
        }

        public TaskBuilder<I, W, R, O> aggregate(String tvgName, String functionName, String inputVariable, String outputVariable) {
            aggregations.add(new AggregationContainer(prefixes.expandIfPrefixed(tvgName), functionName.toUpperCase(), inputVariable, outputVariable));
            return this;
        }


        public TaskOperatorAPIImpl<I, W, R, O> build() {
            return new TaskOperatorAPIImpl<I, W, R, O>(this);
        }


    }


    public static class MonitoringTaskBuilder<I, W, R, O> extends TaskBuilder<I, W, R, O>{
        public TaskBuilder<I, W, R, O> addR2R(String tvgName, RelationToRelationOperator<W, R> r2r) {
            RelationToRelationOperator<W, R> monitorProxy = new MonitoringR2RProxy<>(r2r);
            return super.addR2R(tvgName,monitorProxy);
        }
        public TaskBuilder<I, W, R, O> addR2R(List<String> tvgNames, RelationToRelationOperator<W, R> r2r) {
            RelationToRelationOperator<W, R> monitorProxy = new MonitoringR2RProxy<>(r2r);
            return super.addR2R(tvgNames,monitorProxy);
        }
        public TaskBuilder<I, W, R, O> addS2R(String sourceURI, StreamToRelationOp<I, W> s2r, String tvgName) {
            StreamToRelationOp<I,W> monitorProxy = new MonitoringS2RProxy<>(s2r);
            return super.addS2R(sourceURI,monitorProxy, tvgName);
        }
        public TaskBuilder<I, W, R, O> addR2S(String sinkURI, RelationToStreamOperator<R, O> r2s) {
            RelationToStreamOperator<R,O> monitorProxy = new MonitoringR2SProxy<>(r2s);
            return super.addR2S(sinkURI,monitorProxy);
        }
    }


}
