package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.IRI;
import org.apache.jena.sparql.engine.join.Join;
import org.apache.log4j.Logger;
import org.streamreasoning.rsp4j.abstraction.containers.AggregationContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2RContainer;
import org.streamreasoning.rsp4j.abstraction.containers.R2SContainer;
import org.streamreasoning.rsp4j.abstraction.containers.S2RContainer;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunction;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.DataSet;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionObserver;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.JoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContinuousProgram<I, W, R, O> extends ContinuousQueryExecutionObserver<I, W, R, O> {

    private static final Logger log = Logger.getLogger(ContinuousProgram.class);
    private List<Task<I, W, R, O>> tasks;
    private Map<String,DataStream<I>> inputStreams;
    private DataStream<O> outputStream;
    private SDS<W> sds;
    private JoinAlgorithm<R> joinAlgorithm;
    private Map<String,Collection<R>> cachedStaticBindings;

  public ContinuousProgram(ContinuousProgramBuilder builder) {
    super(builder.sds, null);
    this.tasks = builder.tasks;
    this.inputStreams = builder.inputStreams;
    this.outputStream = builder.outputStream;
    if (builder.sds != null) {
      this.sds = builder.sds;
    } else {
      this.sds = (SDS<W>) new SDSImpl();
    }
    this.joinAlgorithm = builder.joinAlgorithm;
    this.cachedStaticBindings = new HashMap<>();
    linkStreamsToOperators();
    evaluateDefaultGraph();
  }

  private void linkStreamsToOperators() {
    for (Task<I, W, R, O> task : tasks) {
      Set<S2RContainer<I, W>> s2rs = task.getS2Rs();
      for (S2RContainer<I, W> s2rContainer : s2rs) {
        String streamURI = s2rContainer.getSourceURI();
        String tvgName = s2rContainer.getTvgName();
        IRI iri = RDFUtils.createIRI(tvgName);
        if (inputStreams.containsKey(streamURI)) {
          DataStream<I> consumedStream = inputStreams.get(streamURI);
          TimeVarying<W> tvg = s2rContainer.<I, W>getS2rOperator().link(this).apply(consumedStream);

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
  private void evaluateDefaultGraph(){
    for (Task<I, W, R, O> task : tasks) {
      for(R2RContainer<W,R> r2r : task.getR2Rs()){
        if(r2r.getTvgName().equals("default")){
          DataSet<W> defaultGraph = task.getDefaultGraph();
          Stream<R> staticBindings = r2r.getR2rOperator().eval(defaultGraph.getContent().stream());
          cachedStaticBindings.put("default", staticBindings.collect(Collectors.toSet()));
        }
      }

    }
  }

  @Override
  public void update(Observable o, Object arg) {
    Long now = (Long) arg;
    for (Task<I, W, R, O> task : tasks) {
      Set<R2SContainer<R, O>> r2ss = task.getR2Ss();
      for (R2SContainer<R, O> r2s : r2ss) {
        if (task.getAggregations().isEmpty()) {
          eval(now)
              .forEach(o1 -> outstream().put((O) r2s.getR2sOperator().transform(o1, now), now));
        } else {
          handleAggregations(task, r2s, now);
        }
      }
    }
  }

  private void handleAggregations(
          Task<I, W, R, O> task, R2SContainer<R, O> r2s, long timestamp) {
    Set<R> collection = eval(timestamp).collect(Collectors.toSet());
    for (AggregationContainer<R> aggregationContainer : task.getAggregations()) {
      Optional<R> aggregation = evaluateAggregation(collection, aggregationContainer);
      aggregation.ifPresent(
          r -> outputStream.put(r2s.getR2sOperator().transform(r, timestamp), timestamp));
    }
  }

  private Optional<R> evaluateAggregation(
      Collection<R> collection, AggregationContainer<R> aggregationContainer) {
    AggregationFunctionRegistry functionRegistry = AggregationFunctionRegistry.getInstance();
    Optional<AggregationFunction<R>> aggregationFunction =
        functionRegistry.getFunction(aggregationContainer.getFunctionName());
    String inputVariable = aggregationContainer.getInputVariable();
    String outputVariable = aggregationContainer.getOutputVariable();

    Optional<R> r =
        aggregationFunction.map(af -> af.evaluate(inputVariable, outputVariable, collection));
    if (!r.isPresent()) {
      log.error("Function " + aggregationContainer.getFunctionName() + " not found in Registry!");
    }
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
    Set<R> result = Collections.emptySet();
    Task<I, W, R, O> iroTask = tasks.get(0);
    boolean isFirst = true;
    if (!iroTask.getR2Rs().isEmpty()) {
      Map<String, W> tvgMap = sds.asTimeVaryingEs().stream().collect(Collectors.toMap(TimeVarying::iri, TimeVarying::get));
      for (R2RContainer<W, R> r2RContainer : iroTask.getR2Rs()) {
        RelationToRelationOperator<W, R> r2rOperator = r2RContainer.getR2rOperator();
        String tvgTaskName = r2RContainer.getTvgName();
        if(tvgTaskName.equals("default")){
          result = new HashSet<>(cachedStaticBindings.get("default"));
        }
        else if (tvgMap.containsKey(tvgTaskName)) {
          Stream<W> tvgStream = Stream.of(tvgMap.get(tvgTaskName));
          result = checkAndMergeR2REval(result, r2rOperator, tvgStream, isFirst);
        }
        if (tvgMap.keySet().isEmpty()) {
          // no windows defined
          result = checkAndMergeR2REval(result,  r2rOperator, sds.toStream(), isFirst);
        }
        isFirst = false;
      }
    } else {
      log.error("No R2R operator defined!");

    }
    return result.stream();
  }

  private Set<R> checkAndMergeR2REval(Set<R> result, RelationToRelationOperator<W, R> r2rOperator, Stream<W> wStream, boolean isFirst) {
    Set<R> currentResult = r2rOperator.eval(wStream).collect(Collectors.toSet());
    if (result.isEmpty() && isFirst) {
      result = currentResult;
    } else {
      result = joinAlgorithm.join(result, currentResult);
    }
    return result;
  }

  public static class ContinuousProgramBuilder<I, W, R, O> {
    private List<TaskAbstractionImpl<I, W, R, O>> tasks;
    private Map<String,DataStream<I>> inputStreams;
    private DataStream<O> outputStream;
    private SDS<I> sds;
    private JoinAlgorithm<R> joinAlgorithm;

    public ContinuousProgramBuilder() {
      tasks = new ArrayList<>();
      inputStreams = new HashMap<>();
    }

    public ContinuousProgramBuilder<I, W, R, O> in(DataStream<I> stream) {
      this.inputStreams.put(stream.getName(),stream);
      return this;
    }

    public ContinuousProgramBuilder<I, W, R, O> out(DataStream<O> stream) {
      this.outputStream = stream;
      return this;
    }

    public ContinuousProgramBuilder<I, W, R, O> addTask(TaskAbstractionImpl<I, W, R, O> task) {
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

    public ContinuousProgramBuilder<I, W, R, O> addJoinAlgorithm(JoinAlgorithm<R> joinAlgorithm){
      this.joinAlgorithm = joinAlgorithm;
      return this;
    }
  }
}
