package it.polimi.deib.rsp.simple;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.stream.data.WebDataStream;
import lombok.extern.log4j.Log4j;
import org.apache.commons.rdf.api.Triple;

import java.util.Observable;

/**
 * Created by Riccardo on 12/08/16.
 */

@Log4j
public class ContinuousQueryExecutionImpl extends ContinuousQueryExecutionObserver {

    private final RelationToStreamOperator<Triple> r2s;
    private final RelationToRelationOperator<Triple> r2r;
    private final SDS sds;
    private final ContinuousQuery query;
    private StreamToRelationOperator[] s2rs;

    public ContinuousQueryExecutionImpl(SDS sds, ContinuousQuery query, RelationToRelationOperator r2r, RelationToStreamOperator r2s, StreamToRelationOperator... s2rs) {
        super(sds, query);
        this.s2rs = s2rs;
        this.query = query;
        this.sds = sds;
        this.r2r = r2r;
        this.r2s = r2s;
    }

    @Override
    public WebDataStream<Triple> outstream() {
        return null;
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return query;
    }

    @Override
    public SDS getSDS() {
        return sds;
    }

    @Override
    public StreamToRelationOperator<Triple, Triple>[] getS2R() {
        return s2rs;
    }

    @Override
    public RelationToRelationOperator<Triple> getR2R() {
        return r2r;
    }

    @Override
    public RelationToStreamOperator<Triple> getR2S() {
        return r2s;
    }

    @Override
    public void add(QueryResultFormatter o) {
        addObserver(o);
    }

    @Override
    public void remove(QueryResultFormatter o) {
        deleteObserver(o);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg;

        r2r.eval(now).forEach(o1 -> {
            Triple eval1 = r2s.eval(o1, now);
            setChanged();
            notifyObservers(eval1);
            if (outstream() != null) {
                outstream().put(eval1, now);
            }
        });

    }
}

