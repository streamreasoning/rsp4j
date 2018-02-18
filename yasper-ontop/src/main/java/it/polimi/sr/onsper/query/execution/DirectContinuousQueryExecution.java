package it.polimi.sr.onsper.query.execution;

import it.polimi.rspql.cql._2s._ToStreamOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.rspql.querying.ContinuousQueryExecution;
import it.polimi.rspql.querying.SDS;
import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.sr.onsper.query.OBDAQuery;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import it.polimi.yasper.core.reasoning.TVGReasoner;
import it.unibz.inf.ontop.answering.reformulation.impl.SQLExecutableQuery;
import it.unibz.inf.ontop.answering.reformulation.input.InputQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

//Bypass ontop and execute the query directly at the source
public class DirectContinuousQueryExecution extends Observable implements Observer, ContinuousQueryExecution {

    private final SDS sds;
    private final InputQuery q;
    private final ContinuousQuery query;
    private final SQLExecutableQuery sql;
    private PreparedStatement statement;
    private final Connection calciteConnection;

    public DirectContinuousQueryExecution(SDS sds, OBDAQuery query, InputQuery sparql_query, SQLExecutableQuery sql_query, Connection calciteConnection) {
        this.sds = sds;
        this.query = query;
        this.q = sparql_query;
        this.calciteConnection = calciteConnection;
        this.sql = sql_query;
        try {
            this.statement = calciteConnection.prepareStatement(sql_query.getSQL());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        try {
            ResultSet resultSet = statement.executeQuery();
            //TODO pass the result set to ontop to apply the queryclause and get the results
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner) {
        return eval(ts, sds, q);
    }

    @Override
    public InstantaneousResponse eval(long ts, SDS sds, ContinuousQuery q, TVGReasoner reasoner, _ToStreamOperator s2r) {
        try {
            ResultSet resultSet = statement.executeQuery();
            //new SelectResponse("http://streamreasoning.org/heaven/", query, results, ts);
            //return s2r.eval(last_response);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
        item.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        Long ts = (Long) arg;

        this.sds.beforeEval();
        TVGReasoner ontop = null;

        _ToStreamOperator s2r = null;

        InstantaneousResponse r = eval(ts, this.sds, this.query, ontop, s2r);
        this.sds.afterEval();

        setChanged();
        notifyObservers(r);
    }

}
