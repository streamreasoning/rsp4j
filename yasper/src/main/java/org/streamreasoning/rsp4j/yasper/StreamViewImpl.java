package org.streamreasoning.rsp4j.yasper;

import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;
import java.util.Observer;
import java.util.stream.Stream;

public class StreamViewImpl extends Observable implements ContinuousQueryExecution<Triple,Triple,Triple>, Observer {

    private Content content;

    @Override
    public void update(Observable o, Object arg) {
        StreamToRelationOp window_streamToRelationOp = (StreamToRelationOp) o;
        this.content = window_streamToRelationOp.content((Long) arg);
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }


    @Override
    public WebDataStream<Triple> outstream() {
        return null;
    }

    @Override
    public ContinuousQuery query() {
        return null;
    }


    @Override
    public SDS sds() {
        return null;
    }

    @Override
    public StreamToRelationOp<Triple,Triple>[] s2rs() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToRelationOperator<Triple> r2r() {
        return null;
    }

    @Override
    public RelationToStreamOperator<Triple> r2s() {
        return null;
    }

    @Override
    public void add(StreamToRelationOp<Triple, Triple> op) {

    }

    @Override
    public Stream<SolutionMapping<Triple>> eval(Long now) {
        return null;
    }
}
