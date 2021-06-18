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
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionObserver;
import org.streamreasoning.rsp4j.yasper.querying.SelectInstResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class ContinuousProgram<I, R, O> extends ContinuousQueryExecutionObserver<I, R, O> {

  private List<Task<I, R, O>> tasks;
  private WebDataStream<I> inputStream;
  private WebDataStream<O> outputStream;
  private SDS<R> sds;

  public ContinuousProgram(ContinuousProgramBuilder builder) {
    super(builder.sds, null);
    this.tasks = builder.tasks;
    this.inputStream = builder.inputStream;
    this.outputStream = builder.outputStream;
    this.sds = builder.sds;

    linkStreamsToOperators();
  }

  private void linkStreamsToOperators() {
    for (Task<I, R, O> task : tasks) {
      Set<Task.S2RContainer<I, R>> s2rs = task.<I, R>getS2Rs();
      for (Task.S2RContainer<I, R> s2rContainer : s2rs) {
        String streamURI = s2rContainer.getSourceURI();
        String tvgName = s2rContainer.getTvgName();
        IRI iri = RDFUtils.createIRI(streamURI);

        if (inputStream != null) {
          TimeVarying<R> tvg = s2rContainer.<I, R>getS2rOperator().link(this).apply(inputStream);

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
    for (Task<I, R, O> task : tasks) {
      Set<Task.R2SContainer<O>> r2ss = task.getR2Ss();
      for (Task.R2SContainer<O> r2s : r2ss) {
        if (task.getAggregations().isEmpty()) {
          eval(now).forEach(o1 -> outstream().put((O) r2s.getR2sOperator().eval(o1, now), now));
        } else {
          handleAggregations(task,r2s,now);
        }
      }
    }
  }
  private void handleAggregations(Task<I, R, O> task,Task.R2SContainer<O> r2s, long timestamp ){
    Set<SolutionMapping<O>> collection = eval(timestamp).collect(Collectors.toSet());
    for (Task.AggregationContainer<O> aggregationContainer : task.getAggregations()) {
      Optional<SolutionMapping<O>> aggregation = evaluateAggregation(collection, aggregationContainer);
      if (aggregation.isPresent()) {
        outputStream.put((O) r2s.getR2sOperator().eval(aggregation.get(), timestamp), timestamp);
      }
    }
  }
  private Optional<SolutionMapping<O>> evaluateAggregation(Collection<SolutionMapping<O>> collection, Task.AggregationContainer<O> aggregationContainer) {
    AggregationFunctionRegistry functionRegistry = AggregationFunctionRegistry.getInstance();
    Optional<AggregationFunction> aggregationFunction =
            functionRegistry.getFunction(aggregationContainer.getFunctionName());
    SolutionMapping<O> aggregation = null;
    if (aggregationFunction.isPresent()) {
      aggregation =
              aggregationFunction
                      .get()
                      .evaluate(
                              aggregationContainer.getInputVariable(),
                              aggregationContainer.getOutputVariable(),
                              collection);

    } else {
      log.error("Function " + aggregationContainer.getFunctionName() + " not found in Registry!");
    }
    return Optional.ofNullable(aggregation);
  }
  @Override
  public WebDataStream<O> outstream() {
    return outputStream;
  }

  @Override
  public ContinuousQuery query() {
    return null;
  }

  @Override
  public SDS<R> sds() {
    return sds;
  }

  @Override
  public StreamToRelationOp<I, R>[] s2rs() {
    return new StreamToRelationOp[0];
  }

  @Override
  public RelationToRelationOperator<R, O> r2r() {
    return null;
  }

  @Override
  public RelationToStreamOperator<O> r2s() {
    return null;
  }

  @Override
  public void add(StreamToRelationOp<I, R> op) {
    op.link(this);
  }

  public Stream<SolutionMapping<O>> eval(Long now) {
    sds.materialize(now);
    Task<I, R, O> iroTask = tasks.get(0);
    RelationToRelationOperator<R, O> r2rOperator = iroTask.getR2Rs().get(0).getR2rOperator();
    Stream<O> eval = r2rOperator.eval(sds.toStream());
    // TODO this is conflcting

    Stream<SolutionMapping<O>> rStream = eval.map(r -> r2rOperator.createSolutionMapping(r));
    return rStream;
  }

  public static class ContinuousProgramBuilder<I, R, O> {
    private List<Task<I, R, O>> tasks;
    private WebDataStream<I> inputStream;
    private WebDataStream<O> outputStream;
    private SDS<I> sds;

    public ContinuousProgramBuilder() {
      tasks = new ArrayList<>();
    }

    public ContinuousProgramBuilder<I, R, O> in(WebDataStream<I> stream) {
      this.inputStream = stream;
      return this;
    }

    public ContinuousProgramBuilder<I, R, O> out(WebDataStream<O> stream) {
      this.outputStream = stream;
      return this;
    }

    public ContinuousProgramBuilder<I, R, O> addTask(Task<I, R, O> task) {
      tasks.add(task);
      return this;
    }

    public ContinuousProgramBuilder<I, R, O> setSDS(SDS<I> sds) {
      this.sds = sds;
      return this;
    }

    public ContinuousProgram<I, R, O> build() {
      return new ContinuousProgram<I, R, O>(this);
    }
  }
}
