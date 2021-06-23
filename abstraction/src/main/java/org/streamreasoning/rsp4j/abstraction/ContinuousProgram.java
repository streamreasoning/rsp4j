package org.streamreasoning.rsp4j.abstraction;

import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunction;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionObserver;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class ContinuousProgram<I, W, R, O> extends ContinuousQueryExecutionObserver<I, W, R, O> {

    private List<Task<I, W, R, O>> tasks;
    private DataStream<I> inputStream;
    private DataStream<O> outputStream;
    private SDS<W> sds;

    public ContinuousProgram(ContinuousProgramBuilder builder) {
        super(builder.sds, null);
        this.tasks = builder.tasks;
        this.inputStream = builder.inputStream;
        this.outputStream = builder.outputStream;
        if (builder.sds != null) {
            this.sds = builder.sds;
        } else {
            this.sds = (SDS<W>) new SDSImpl();
        }

        linkStreamsToOperators();
    }

    private void linkStreamsToOperators() {
        for (Task<I, W, R, O> task : tasks) {
            Set<Task.S2RContainer<I, W>> s2rs = task.<I, W>getS2Rs();
            for (Task.S2RContainer<I, W> s2rContainer : s2rs) {
                String streamURI = s2rContainer.getSourceURI();
                String tvgName = s2rContainer.getTvgName();
                IRI iri = RDFUtils.createIRI(streamURI);
                Collection<TimeVarying<W>> vgs = sds.asTimeVaryingEs();
                if (inputStream != null) {
                    TimeVarying<W> tvg = s2rContainer.<I, W>getS2rOperator().link(this).apply(inputStream);

                    if (tvg.named()) {
                        sds.add(iri, tvg);
                    } else {
                        sds.add(tvg);
                    }
                } else {
                    log.error(String.format("No stream found for IRI %s", streamURI));
                }
            }
        }
    }


    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg;
        for (Task<I, W, R, O> task : tasks) {
            Set<Task.R2SContainer<R, O>> r2ss = task.getR2Ss();
            for (Task.R2SContainer<R, O> r2s : r2ss) {
                if (task.getAggregations().isEmpty()) {
                    eval(now).forEach(o1 -> outstream().put((O) r2s.getR2sOperator().transform(o1, now), now));
                } else {
                    handleAggregations(task, r2s, now);
                }
            }
        }
    }

    private void handleAggregations(Task<I, W, R, O> task, Task.R2SContainer<R, O> r2s, long timestamp) {
        Set<R> collection = eval(timestamp).collect(Collectors.toSet());
        for (Task.AggregationContainer<R> aggregationContainer : task.getAggregations()) {
            Optional<R> aggregation = evaluateAggregation(collection, aggregationContainer);
            aggregation.ifPresent(r -> outputStream.put(r2s.getR2sOperator().transform(r, timestamp), timestamp));
        }
    }

    private Optional<R> evaluateAggregation(Collection<R> collection, Task.AggregationContainer<R> aggregationContainer) {
        AggregationFunctionRegistry functionRegistry = AggregationFunctionRegistry.getInstance();
        Optional<AggregationFunction<R>> aggregationFunction =
                functionRegistry.getFunction(aggregationContainer.getFunctionName());
        String inputVariable = aggregationContainer.getInputVariable();
        String outputVariable = aggregationContainer.getOutputVariable();

        Optional<R> r = aggregationFunction
                .map(af -> af.evaluate(inputVariable, outputVariable, collection));

        log.error("Function " + aggregationContainer.getFunctionName() + " not found in Registry!");

        return r;
    }

    @Override
    public DataStream<O> outstream() {
        return outputStream;
    }

    @Override
    public TimeVarying<Collection<R>> output() {
        return null;
    }

    @Override
    public ContinuousQuery query() {
        return null;
    }

    @Override
    public SDS<W> sds() {
        return sds;
    }

    @Override
    public StreamToRelationOp<I, W>[] s2rs() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToRelationOperator<W, R> r2r() {
        return null;
    }

    @Override
    public RelationToStreamOperator<R, O> r2s() {
        return null;
    }

    @Override
    public void add(StreamToRelationOp<I, W> op) {
        op.link(this);
    }

    public Stream<R> eval(Long now) {
        sds.materialize(now);
        Task<I, W, R, O> iroTask = tasks.get(0);
        RelationToRelationOperator<W, R> r2rOperator = iroTask.getR2Rs().get(0).getR2rOperator();
        Stream<R> eval = r2rOperator.eval(sds.toStream());
        // TODO this is conflcting
        return eval;
    }

    public static class ContinuousProgramBuilder<I, W, R, O> {
        private List<Task<I, W, R, O>> tasks;
        private DataStream<I> inputStream;
        private DataStream<O> outputStream;
        private SDS<I> sds;

        public ContinuousProgramBuilder() {
            tasks = new ArrayList<>();
        }

        public ContinuousProgramBuilder<I, W, R, O> in(DataStream<I> stream) {
            this.inputStream = stream;
            return this;
        }

        public ContinuousProgramBuilder<I, W, R, O> out(DataStream<O> stream) {
            this.outputStream = stream;
            return this;
        }

        public ContinuousProgramBuilder<I, W, R, O> addTask(Task<I, W, R, O> task) {
            tasks.add(task);
            return this;
        }

        public ContinuousProgramBuilder<I, W, R, O> setSDS(SDS<I> sds) {
            this.sds = sds;
            return this;
        }

        public ContinuousProgram<I, W, R, O> build() {
            return new ContinuousProgram<I, W, R, O>(this);
        }
    }
}
