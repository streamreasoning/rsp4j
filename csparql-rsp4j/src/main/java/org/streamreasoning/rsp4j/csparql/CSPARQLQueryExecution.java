package org.streamreasoning.rsp4j.csparql;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.Collection;
import java.util.stream.Stream;

public class CSPARQLQueryExecution <O> implements ContinuousQueryExecution<Graph, Graph,Binding, O> {

    private final ContinuousQuery q;
    private final DataStream<O> outpuStream;

    public CSPARQLQueryExecution(ContinuousQuery q, DataStream<O> outputStream){
        this.q = q;
        this.outpuStream = outputStream;
    }

    @Override
    public DataStream<O> outstream() {
        return outpuStream;
    }

    @Override
    public TimeVarying<Collection<Binding>> output() {
        return null;
    }

    @Override
    public ContinuousQuery query() {
        return q;
    }

    @Override
    public SDS<Graph> sds() {
        return null;
    }

    @Override
    public StreamToRelationOp<Graph, Graph>[] s2rs() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToRelationOperator<Graph, Binding> r2r() {
        return null;
    }

    @Override
    public RelationToStreamOperator<Binding, O> r2s() {
        return null;
    }

    @Override
    public void add(StreamToRelationOp<Graph, Graph> op) {

    }

    @Override
    public Stream<Binding> eval(Long now) {
        return null;
    }
}
