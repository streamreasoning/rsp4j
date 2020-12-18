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
        this.content = window_streamToRelationOp.getContent((Long) arg);
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
    public ContinuousQuery getContinuousQuery() {
        return null;
    }


    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public StreamToRelationOperatorFactory<Triple,Triple>[] getS2R() {
        return new StreamToRelationOperatorFactory[0];
    }

    @Override
    public RelationToRelationOperator<Triple> getR2R() {
        return null;
    }

    @Override
    public RelationToStreamOperator<Triple>getR2S() {
        return null;
    }

    @Override
    public void add(QueryResultFormatter o) {
    }

    @Override
    public void remove(QueryResultFormatter o) {

    }
}
