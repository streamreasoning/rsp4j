package it.polimi.jasper.engine.querying.execution.observer;

import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.SDS;
import it.polimi.yasper.core.quering.execution.ContinuousQueryExecutionObserver;
import it.polimi.yasper.core.quering.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.core.Quad;
import org.apache.jena.sparql.util.Context;

import java.util.Iterator;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by riccardo on 03/07/2017.
 */
public abstract class JenaContinuousQueryExecution extends ContinuousQueryExecutionObserver implements QueryExecution {

    protected final  Query q;
    protected InstantaneousResponse last_response = null;
    protected QueryExecution execution;

    public JenaContinuousQueryExecution(ContinuousQuery query, SDS sds, TVGReasoner reasoner, RelationToStreamOperator s2r) {
        super(sds, query, reasoner, s2r);
        this.q = QueryFactory.create(query.getSPARQL());
    }

    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;

        this.sds.beforeEval();
        InstantaneousResponse r = eval(ts);
        this.sds.afterEval();

        setChanged();
        notifyObservers(r);
    }


    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public void setInitialBinding(QuerySolution binding) {

    }

    @Override
    public Dataset getDataset() {
        return (Dataset) sds;
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
