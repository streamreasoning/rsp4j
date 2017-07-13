package it.polimi.jasper.engine.query.execution;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.reasoning.TimeVaryingInfGraph;
import it.polimi.jasper.engine.sds.JenaSDS;
import it.polimi.yasper.core.SDS;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.query.execution.ContinuousQueryExecutionImpl;
import it.polimi.yasper.core.query.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.util.Context;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousJenaQueryExecution extends ContinuousQueryExecutionImpl implements QueryExecution {

    protected JenaSDS sds;
    protected Query q;
    protected InstantaneousResponse last_response = null;
    protected QueryExecution execution;

    public ContinuousJenaQueryExecution(RSPQuery query, Query q, JenaSDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(query, reasoner, s2r);
        this.sds = sds;
        this.q = q;
    }

    public void materialize(TimeVaryingGraph tvg) {
        if (reasoner != null) {
            InstantaneousItem g = tvg.getGraph();
            if (g instanceof TimeVaryingInfGraph) {
                ((TimeVaryingInfGraph) g).rebind();
            }
        }
    }

    @Override
    public void eval(SDS sds, long ts) {
        eval(sds, null, ts);
    }

    @Override
    public void setInitialBinding(QuerySolution binding) {

    }

    @Override
    public Dataset getDataset() {
        return sds;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public Query getQuery() {
        return q;
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
