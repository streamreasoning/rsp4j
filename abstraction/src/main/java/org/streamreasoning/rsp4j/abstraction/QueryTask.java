package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.Dstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Istream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

public class QueryTask extends Task<Graph, Graph, Binding, Binding> {

  public QueryTask(TaskBuilder<Graph, Graph, Binding, Binding> builder) {
    super(builder);
  }

  public static class QueryTaskBuilder extends TaskBuilder<Graph, Graph, Binding, Binding> {

    public QueryTaskBuilder() {
      super();
    }

    public QueryTaskBuilder fromQuery(ContinuousQuery<Graph, Graph, Binding, Binding> query) {
      // add S2R
      Report report = new ReportImpl();
      report.add(new OnWindowClose());

      Tick tick = Tick.TIME_DRIVEN;
      ReportGrain report_grain = ReportGrain.SINGLE;

      int scope = 0;

      // S2R DECLARATION

      query.getWindowMap().entrySet().stream()
          .forEach(
              entry -> {
                StreamToRelationOp<Graph, Graph> s2r =
                    new CSPARQLStreamToRelationOp<Graph, Graph>(
                        RDFUtils.createIRI(entry.getKey().iri()),
                        entry.getKey().getRange(),
                        entry.getKey().getStep(),
                        TimeFactory.getInstance(),
                        tick,
                        report,
                        report_grain,
                        new GraphContentFactory());
                this.addS2R(entry.getValue().getName(), s2r, entry.getKey().iri());
              });
      // R2R DECLARATION
      this.addR2R(null, query.r2r()); // TODO restrict TVG
      RelationToStreamOperator<Binding, Binding> r2s = getR2RFromQuery(query);
      if (r2s != null) {
        this.addR2S(query.getOutputStream().getName(), r2s);
      }
      //Add aggregations
      query.getAggregations().forEach(a -> aggregate(a.getTvg(),a.getFunctionName(),a.getInputVariable(),a.getOutputVariable()));
      //Add standard aggregations
      AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());
      return this;
    }

    private RelationToStreamOperator<Binding, Binding> getR2RFromQuery(
        ContinuousQuery<Graph, Graph, Binding, Binding> query) {
      RelationToStreamOperator<Binding, Binding> r2s = null;
      if (query.isIstream()) {
        r2s = Istream.get();
      } else if (query.isDstream()) {
        r2s = Dstream.get();
      } else if (query.isRstream()) {
        r2s = Rstream.get();
      }
      return r2s;
    }
  }
}
