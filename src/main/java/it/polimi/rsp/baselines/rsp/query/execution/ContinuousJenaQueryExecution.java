package it.polimi.rsp.baselines.rsp.query.execution;

import it.polimi.rsp.baselines.rsp.sds.SDS;
import it.polimi.rsp.baselines.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.sr.rsp.RSPQuery;
import it.polimi.streaming.Response;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.reasoner.InfGraph;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.util.Context;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class ContinuousJenaQueryExecution extends ContinuousQueryExecutionImpl {

    protected RSPQuery query;
    protected Query q;
    protected SDS sds;
    protected Response last_response = null;
    protected QueryExecution execution;
    protected Reasoner reasoner;

    public ContinuousJenaQueryExecution(RSPQuery query, SDS sds, Reasoner reasoner) {
        this.query = query;
        this.q = query.getQ();
        this.sds = sds;
        this.reasoner = reasoner;
    }

    public ContinuousJenaQueryExecution(RSPQuery query, SDS sds) {
        this(query, sds, null);
    }


    @Override
    public void materialize(TimeVaryingGraph tvg) {
        if (reasoner != null) {
            InfGraph g = reasoner.bind(tvg.getGraph());
            tvg.setGraph(g);
            g.rebind();
        }
    }

    @Override
    public void eval(SDS sds, long ts) {
        eval(sds, null, ts);
    }

    @Override
    public void bindTbox(Model tbox) {
        if(reasoner!=null)
            this.reasoner = this.reasoner.bindSchema(tbox);
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
