package it.polimi.sr.rsp.csparql.operators;

import it.polimi.jasper.querying.results.SolutionMappingImpl;
import it.polimi.sr.rsp.csparql.syntax.RSPQLJenaQuery;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.sds.SDS;
import lombok.extern.log4j.Log4j;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.ext.com.google.common.collect.Streams;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.Triple;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.impl.InfModelImpl;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.sparql.core.DatasetGraphFactory;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.util.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Log4j
public class R2ROperatorSPARQLEnt implements RelationToRelationOperator<Binding>, QueryExecution {

    private final RSPQLJenaQuery query;
    private final SDS<Graph> sds;
    private final Dataset ds;
    private final String baseURI;
    public final List<String> resultVars;
    private QueryExecution execution;
    private final Reasoner reasoner;

    public R2ROperatorSPARQLEnt(RSPQLJenaQuery query, Reasoner reasoner, SDS<Graph> sds, String baseURI) {
        this.query = query;
        this.sds = sds;
        this.reasoner = reasoner;
        MultiUnion graph = new MultiUnion();
        ds = DatasetFactory.wrap(DatasetGraphFactory.create(graph));
        sds.asTimeVaryingEs().forEach(tvg -> {
            InfGraph infGraph = new InfModelImpl(this.reasoner.bind(tvg.get())).getInfGraph();
            if (tvg.named()) {
                ds.addNamedModel(tvg.iri(), new InfModelImpl(infGraph));
            } else {
                ((MultiUnion) ds.getDefaultModel().getGraph()).addGraph(infGraph);
            }
        });

        this.baseURI = baseURI;
        this.resultVars = query.getResultVars();

    }

    @Override
    public Stream<SolutionMapping<Binding>> eval(long ts) {
        //TODO fix up to stream
        String id = baseURI + "result/" + ts;
        this.execution = QueryExecutionFactory.create(query, ds);
        return Streams.stream(this.execution.execSelect()).map(querySolution -> ((org.apache.jena.sparql.core.ResultBinding) querySolution).getBinding()).map(b -> new SolutionMappingImpl(id, b, this.resultVars, ts));
    }

    private List<Binding> getSolutionSet(ResultSet results) {

        List<Binding> solutions = new ArrayList<>();
        while (results.hasNext()) {
            solutions.add(results.nextBinding());
        }
        return solutions;
    }

    @Override
    public void setInitialBinding(QuerySolution binding) {

    }

    @Override
    public void setInitialBinding(Binding binding) {
        execution.setInitialBinding(binding);
    }

    @Override
    public Dataset getDataset() {
        return ds;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public Query getQuery() {
        return query;
    }

    @Override
    public ResultSet execSelect() {
        return execution.execSelect();
    }

    @Override
    public Model execConstruct() {
        return execution.execConstruct();
    }

    @Override
    public Model execConstruct(Model model) {
        return execution.execConstruct(model);
    }

    @Override
    public Iterator<Triple> execConstructTriples() {
        return execution.execConstructTriples();
    }

    @Override
    public Iterator<Quad> execConstructQuads() {
        return execution.execConstructQuads();
    }

    @Override
    public Dataset execConstructDataset() {
        return execution.execConstructDataset();
    }

    @Override
    public Dataset execConstructDataset(Dataset dataset) {
        return execution.execConstructDataset(dataset);
    }

    @Override
    public Model execDescribe() {
        return execution.execDescribe();
    }

    @Override
    public Model execDescribe(Model model) {
        return execution.execDescribe(model);
    }

    @Override
    public Iterator<Triple> execDescribeTriples() {
        return execution.execDescribeTriples();
    }

    @Override
    public boolean execAsk() {
        return execution.execAsk();
    }

    @Override
    public JsonArray execJson() {
        return execution.execJson();
    }

    @Override
    public Iterator<JsonObject> execJsonItems() {
        return execution.execJsonItems();
    }

    @Override
    public void abort() {
        execution.abort();
    }

    @Override
    public void close() {
        execution.close();

    }

    @Override
    public boolean isClosed() {
        return execution.isClosed();
    }

    @Override
    public void setTimeout(long timeout, TimeUnit timeoutUnits) {
        execution.setTimeout(timeout, timeoutUnits);
    }

    @Override
    public void setTimeout(long timeout) {
        execution.setTimeout(timeout);
    }

    @Override
    public void setTimeout(long timeout1, TimeUnit timeUnit1, long timeout2, TimeUnit timeUnit2) {
        execution.setTimeout(timeout1, timeUnit1, timeout2, timeUnit2);
    }

    @Override
    public void setTimeout(long timeout1, long timeout2) {
        execution.setTimeout(timeout1, timeout2);
    }

    @Override
    public long getTimeout1() {
        return execution.getTimeout1();
    }

    @Override
    public long getTimeout2() {
        return execution.getTimeout2();
    }
}
