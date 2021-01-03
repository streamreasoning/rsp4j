package it.polimi.deib.sr.rsp.yasper;

import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.secret.content.Content;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.operators.r2r.RelationToRelationOperator;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQueryExecution;
import it.polimi.deib.sr.rsp.api.operators.r2s.RelationToStreamOperator;
import it.polimi.deib.sr.rsp.api.format.QueryResultFormatter;
import it.polimi.deib.sr.rsp.api.operators.s2r.StreamToRelationOperatorFactory;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;
import java.util.Observer;

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
}
