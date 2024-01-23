package org.streamreasoning.rsp4j.yasper;

import org.apache.log4j.Logger;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.stream.Stream;

/**
 * Created by Riccardo on 12/08/16.
 */

public class ContinuousQueryExecutionImpl<I, W, R, O> extends ContinuousQueryExecutionObserver<I, W, R, O> {

    private static final Logger log = Logger.getLogger(ContinuousQueryExecutionImpl.class);
    private final RelationToStreamOperator<R, O> r2s;
    private final RelationToRelationOperator<W, R> r2r;
    private final SDS<W> sds;
    private final ContinuousQuery query;
    private final DataStream<O> outstream;
    private List<StreamToRelationOp<I, W>> s2rs;

    public ContinuousQueryExecutionImpl(SDS sds, ContinuousQuery query, DataStream<O> outstream, RelationToRelationOperator<W, R> r2r, RelationToStreamOperator<R, O> r2s, StreamToRelationOp<I, W>... s2rs) {
        super(sds, query);
        this.s2rs = Arrays.asList(s2rs);
        this.query = query;
        this.sds = sds;
        this.r2r = r2r;
        this.r2s = r2s;
        this.outstream = outstream;
    }

    @Override
    public DataStream<O> outstream() {
        return outstream;
    }

    @Override
    public TimeVarying<Collection<R>> output() {
        return r2r.apply(sds);
    }

    @Override
    public ContinuousQuery query() {
        return query;
    }

    @Override
    public SDS sds() {
        return sds;
    }

    @Override
    public StreamToRelationOp<I, W>[] s2rs() {
        StreamToRelationOp<I, W>[] a = new StreamToRelationOp[s2rs.size()];
        return s2rs.toArray(a);
    }

    @Override
    public RelationToRelationOperator<W, R> r2r() {
        return r2r;
    }

    @Override
    public RelationToStreamOperator<R, O> r2s() {
        return r2s;
    }

    @Override
    public void add(StreamToRelationOp<I, W> op) {
        op.link(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg;
        r2s.eval(eval(now), now).forEach(o1 -> outstream().put(o1, now));
    }

    @Override
    public Stream<R> eval(Long now) {
        sds.materialize(now);
        return r2r.eval(sds.toStream());
    }
}

