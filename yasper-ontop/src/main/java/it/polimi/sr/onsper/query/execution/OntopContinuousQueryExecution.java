package it.polimi.sr.onsper.query.execution;

import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.sr.onsper.query.OBSDAQueryImpl;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.unibz.inf.ontop.answering.reformulation.input.InputQuery;

import java.util.Observer;

//Execute the query via ontop
public class OntopContinuousQueryExecution implements ContinuousQueryExecution {

    private final SDS sds;
    private final InputQuery q;
    private final ContinuousQuery query;

    public OntopContinuousQueryExecution(SDS sds, OBSDAQueryImpl query, InputQuery q) {
        this.sds = sds;
        this.query = query;
        this.q = q;

    }

    @Override
    public InstantaneousResponse eval(long ts) {
        return eval(ts, sds);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds) {
        return eval(ts, sds, query);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery cq) {

        //    OBDAResultSet execute = sqlQuestStatement.get().execute(this.q);
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner) {
        return eval(ts, sds, q);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r) {
        return eval(ts, sds, q);
    }

    @Override
    public ContinuousQuery getContinuousQuery() {
        return null;
    }

    @Override
    public String getQueryID() {
        return null;
    }

    @Override
    public SDS getSDS() {
        return null;
    }

    @Override
    public _ToStreamOperator getRelationToStreamOperator() {
        return null;
    }

    @Override
    public void addObserver(Observer o) {

    }

    @Override
    public void deleteObserver(Observer o) {

    }

    @Override
    public void add(TimeVarying item) {

    }
}
