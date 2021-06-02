package org.streamreasoning.rsp4j.abstraction.triplepattern;

import org.apache.commons.rdf.api.*;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.sparql.util.FmtUtils;
import org.streamreasoning.rsp4j.abstraction.table.TableResponse;
import org.streamreasoning.rsp4j.abstraction.table.TableRow;
import org.streamreasoning.rsp4j.abstraction.triplepattern.ContinuousTriplePatternQuery;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TriplePatternR2R implements RelationToRelationOperator<TableRow> {
  private final SDS sds;
  private final ContinuousTriplePatternQuery query;
  private final Dataset ds;

  public TriplePatternR2R(SDS sds, ContinuousTriplePatternQuery query) {
    this.sds = sds;
    this.query = query;
    this.ds = (Dataset) sds;
  }

  @Override
  public Stream<SolutionMapping<TableRow>> eval(long ts) {
    Model dataModel = convertToJenaModel();

    return evaluateQuery(dataModel,ts);
  }

  private Model convertToJenaModel(){
    RDF instance = RDFUtils.getInstance();
    JenaRDF jena = new JenaRDF();
    String stream_uri = query.getStreamURI();
    IRI stream =instance.createIRI(stream_uri);
    org.apache.jena.graph.Graph jenaGraph = jena.asJenaGraph(ds.getGraph(stream).get());
    Model dataModel = ModelFactory.createModelForGraph(jenaGraph);
    return dataModel;
  }
  private Stream<SolutionMapping<TableRow>> evaluateQuery(Model dataModel, long ts){
    String queryString = String.format("(bgp (%s))",query.getTriplePattern());
    Op op = SSE.parseOp(queryString) ;
    QueryIterator qIter = Algebra.exec(op,dataModel) ;
    List<Var> vars = getVariableFromQuery().stream().map(v->Var.alloc(v)).collect(Collectors.toList());
    TableRow tableRow = new TableRow();
    List<SolutionMapping<TableRow>> table = new ArrayList<>();
    getVariableFromQuery();
    for ( ; qIter.hasNext() ; )
    {
      Binding b = qIter.nextBinding() ;
      for (Var v : vars) {
        Node n = b.get(v);
        tableRow.add(v.getVarName(), FmtUtils.stringForNode(n));
        table.add(new TableResponse(query.getID() + "/ans/" + ts, ts, tableRow));
      }
    }
    qIter.close() ;
    return table.stream();
  }
  private List<String> getVariableFromQuery(){
    String queryString = query.getTriplePattern();
    Pattern pattern = Pattern.compile("\\?([a-zA-Z0-9]+)");
    Matcher matcher = pattern.matcher(queryString);
    List<String> queryVariables = new ArrayList<>();
    while (matcher.find())
    {
      queryVariables.add(matcher.group(1));
    }
    return queryVariables;
  }
}
