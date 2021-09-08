package org.streamreasoning.rsp4j.cqels;

import com.hp.hpl.jena.query.Query;
import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.List;
import java.util.Map;

abstract class CQELSAbstractQuery<O> implements ContinuousQuery<Graph, Binding,Binding,O> {

    private final Query query;

    public CQELSAbstractQuery(Query query){
        this.query = query;
    }
    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIstream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRstream() {
        throw new UnsupportedOperationException();

    }
    public Query getQuery(){
        return query;
    }
    @Override
    public void setDstream() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isIstream() {
        return true;
    }

    @Override
    public boolean isRstream() {
        return false;
    }

    @Override
    public boolean isDstream() {
        return false;
    }

    @Override
    public void setSelect() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setConstruct() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isSelectType() {
        return query.isSelectType();
    }

    @Override
    public boolean isConstructType() {
        return query.isConstructType();
    }



    @Override
    public void setOutputStream(String uri) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<? extends WindowNode, DataStream<Graph>> getWindowMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RelationToRelationOperator<Binding, Binding> r2r() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamToRelationOp<Graph, Binding>[] s2r() {
        return new StreamToRelationOp[0];
    }



    @Override
    public List<Aggregation> getAggregations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RelationToStreamOperator<Binding, O> r2s() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataStream<O> getOutputStream() {
        throw new UnsupportedOperationException();
    }

}
