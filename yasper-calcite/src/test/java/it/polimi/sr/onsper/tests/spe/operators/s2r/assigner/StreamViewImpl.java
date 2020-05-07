package it.polimi.sr.onsper.tests.spe.operators.s2r.assigner;


import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.stream.data.WebDataStream;

import java.util.Observable;
import java.util.Observer;

public class StreamViewImpl extends Observable implements ContinuousQueryExecution, Observer {

    private Content content;

    @Override
    public void update(Observable o, Object arg) {
        Assigner window_assigner = (Assigner) o;
        this.content = window_assigner.getContent((Long) arg);
        setChanged();
        notifyObservers(arg);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
    }


    @Override
    public WebDataStream outstream() {
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
    public StreamToRelationOperator[] getS2R() {
        return new StreamToRelationOperator[0];
    }

    @Override
    public RelationToRelationOperator getR2R() {
        return null;
    }

    @Override
    public RelationToStreamOperator getR2S() {
        return null;
    }

    @Override
    public void add(QueryResultFormatter o) {
    }

    @Override
    public void remove(QueryResultFormatter o) {

    }
}
