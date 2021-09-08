package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CQ<O> implements RSPQL<O> {

    public CQ(){

    }

    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {

    }

    @Override
    public void setIstream() {

    }

    @Override
    public void setRstream() {

    }

    @Override
    public void setDstream() {

    }

    @Override
    public boolean isIstream() {
        return false;
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

    }

    @Override
    public void setConstruct() {

    }

    @Override
    public boolean isSelectType() {
        return false;
    }

    @Override
    public boolean isConstructType() {
        return false;
    }

    @Override
    public DataStream<O> getOutputStream() {
        return null;
    }

    @Override
    public void setOutputStream(String uri) {

    }

    @Override
    public String getID() {
        return null;
    }

    @Override
    public Map<? extends WindowNode, DataStream<Graph>> getWindowMap() {
        return null;
    }

    @Override
    public Time getTime() {
        return null;
    }


    @Override
    public RelationToRelationOperator r2r() {
        return null;
    }

    @Override
    public StreamToRelationOp[] s2r() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToStreamOperator r2s() {
        return null;
    }

    @Override
    public List<Aggregation> getAggregations() {
        return Collections.emptyList();
    }
}