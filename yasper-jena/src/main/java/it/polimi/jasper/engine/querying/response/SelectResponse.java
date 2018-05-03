package it.polimi.jasper.engine.querying.response;

import it.polimi.jasper.engine.querying.execution.observer.TimeVaryingResultSetMem;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.quering.response.InstantaneousResponse;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.jena.query.*;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.ArrayList;
import java.util.List;

@Log
@Getter
public final class SelectResponse extends InstantaneousResponse {

    private List<Binding> solutionSet;
    private ResultSet results;
    protected final List<String> result_vars;


    public SelectResponse(String id, ContinuousQuery query, ResultSet results, List<String> resultVars, long cep_timestamp) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        ResultSetRewindable resultSetRewindable = ResultSetFactory.copyResults(results);
        this.results = resultSetRewindable;
        this.solutionSet = getSolutionSet(resultSetRewindable);
        this.result_vars = resultVars;
    }

    private String getData() {

        String eol = System.getProperty("line.separator");
        String select = "SELECTION getUri()" + eol;

        List<String> resultVars = results.getResultVars();
        if (resultVars != null) {
            for (String r : resultVars) {
                select += "," + r;
            }
        }
        select += eol;
        while (results.hasNext()) {
            QuerySolution next = results.next();
            select += next.toString() + eol;
        }

        return select += ";" + eol;
    }

    @Override
    public InstantaneousResponse difference(InstantaneousResponse new_response) {
        TimeVaryingResultSetMem tvResultSet;
        if (new_response == null) {
            tvResultSet = new TimeVaryingResultSetMem(new ArrayList<>(), result_vars);
        } else {

            SelectResponse remove1 = (SelectResponse) new_response;
            ResultSetRewindable resultSetRewindable = ResultSetFactory.makeRewindable(remove1.getResults());
            resultSetRewindable.reset();

            List<Binding> removeSolutionSet = getSolutionSet(resultSetRewindable);
            resultSetRewindable.reset();

            this.solutionSet.removeAll(removeSolutionSet);

            tvResultSet = new TimeVaryingResultSetMem(this.solutionSet, this.getResults().getResultVars());
        }
        return new SelectResponse(getId(), getQuery(), tvResultSet, result_vars, getCep_timestamp());
    }

    @Override
    public InstantaneousResponse intersection(InstantaneousResponse new_response) {
        TimeVaryingResultSetMem tvResultSet;
        if (new_response == null) {
            tvResultSet = new TimeVaryingResultSetMem(new ArrayList<>(), result_vars);
        } else {

            SelectResponse remove1 = (SelectResponse) new_response;
            ResultSetRewindable rs = ResultSetFactory.makeRewindable(remove1.getResults());
            rs.reset();

            List<Binding> newSolutionBindings = getSolutionSet(rs);
            rs.reset();

            List<Binding> copy = new ArrayList<>(this.solutionSet);
            copy.removeAll(newSolutionBindings);

            this.solutionSet.removeAll(copy);

            tvResultSet = new TimeVaryingResultSetMem(this.solutionSet, result_vars);
        }

        return new SelectResponse(getId(), getQuery(), tvResultSet, result_vars, getCep_timestamp());

    }

    private List<Binding> getSolutionSet(ResultSet results) {
        List<Binding> solutions = new ArrayList<>();
        while (results.hasNext()) {
            solutions.add(results.nextBinding());
        }
        return solutions;
    }


}
