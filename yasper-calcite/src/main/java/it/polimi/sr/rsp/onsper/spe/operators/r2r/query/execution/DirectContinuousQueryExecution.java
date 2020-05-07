package it.polimi.sr.rsp.onsper.spe.operators.r2r.query.execution;

import it.polimi.yasper.core.format.QueryResultFormatter;
import it.polimi.yasper.core.operators.r2r.RelationToRelationOperator;
import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.querying.ContinuousQuery;
import it.polimi.yasper.core.querying.ContinuousQueryExecution;
import it.polimi.yasper.core.querying.result.SolutionMapping;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.unibz.inf.ontop.answering.reformulation.impl.SQLExecutableQuery;
import lombok.extern.java.Log;
import it.polimi.sr.rsp.onsper.spe.operators.r2s.responses.RelationalSolution;
import org.apache.jena.graph.Graph;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
//Bypass rewriting and execute the query directly at the source
public class DirectContinuousQueryExecution extends Observable implements Observer, ContinuousQueryExecution {

    private final SDS sds;
    private final ContinuousQuery query;
    private SQLExecutableQuery sql;
    private PreparedStatement statement;
    private Connection calciteConnection;
    private String sql_string;
    private final RelationToRelationOperator<RelationalSolution.Result> r2r;
    private final RelationToStreamOperator<RelationalSolution.Result> r2s;
    private List<StreamToRelationOperator<Graph, Graph>> s2rs;


    public DirectContinuousQueryExecution(SDS sds, ContinuousQuery query, RelationToRelationOperator<RelationalSolution.Result> r2r, RelationToStreamOperator<RelationalSolution.Result> r2s) {
        this.sds = sds;
        this.query = query;
        this.r2r = r2r;
        this.r2s = r2s;
    }

    @Override
    public void update(Observable o, Object arg) {
        Long now = (Long) arg;
        sds.materialize(now);
        r2r.eval(now).forEach((SolutionMapping<RelationalSolution.Result> ib) -> {
            RelationalSolution.Result eval = r2s.eval(ib, now);
            setChanged();
            if (outstream() != null) {
                outstream().put(eval, now);
            }
            notifyObservers(eval);
        });

    }


    @Override
    public WebDataStream outstream() {
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
    public StreamToRelationOperator[] getS2R() {
        return new StreamToRelationOperator[0];
    }

    @Override
    public RelationToRelationOperator getR2R() {
        return r2r;
    }

    @Override
    public RelationToStreamOperator getR2S() {
        return r2s;
    }

    @Override
    public void add(QueryResultFormatter queryResultFormatter) {
        this.addObserver(queryResultFormatter);
    }

    @Override
    public void remove(QueryResultFormatter queryResultFormatter) {
        this.deleteObserver(queryResultFormatter);
    }

    public void setConnection(SQLExecutableQuery sql_query, Connection calciteConnection) {
        this.sql = sql_query;

        String[] sql = new String[]{this.sql.getSQL().replace("NULL", "''")};

        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(sql[0]);

        Map<String, String> submap = new HashMap<>();
        while (m.find()) {
            String group = m.group(1);
            if (group.contains("||")) {
                String y = group.split("\\|\\|")[0];
                String x = group.split("\\|\\|")[1];
                submap.put("(" + group + ")", "(" +
                        "CAST(" + y + " AS VARCHAR)" +
                        " || " +
                        "CAST(" + x + " AS VARCHAR)" +
                        ")");
            }
        }

        submap.forEach((s1, s2) -> sql[0] = sql[0].replace(s1, s2));

        this.sql_string = sql[0];

        this.calciteConnection = calciteConnection;
        try {
            this.statement = this.calciteConnection.prepareStatement(this.sql_string);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
