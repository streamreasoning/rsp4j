package it.polimi.jasper.engine.query.response;

import it.polimi.jasper.engine.query.RSPQuery;
import it.polimi.jasper.engine.query.execution.TimeVaryingResultSetMem;
import it.polimi.services.FileService;
import it.polimi.yasper.core.query.response.InstantaneousResponse;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetRewindable;
import org.apache.jena.sparql.engine.binding.Binding;

import java.util.ArrayList;
import java.util.List;

@Log
@Getter
public final class SelectResponse extends InstantaneousResponse {

    private List<Binding> solutionSet;
    private ResultSet results;

    public SelectResponse(String id, RSPQuery query, ResultSet results, long cep_timestamp) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        ResultSetRewindable resultSetRewindable = ResultSetFactory.copyResults(results);
        this.results = resultSetRewindable;
        this.solutionSet = getSolutionSet(resultSetRewindable);

    }

    @Override
    public boolean save(String where) {
        return FileService.write(where + ".select", getData());
    }

    private String getData() {

        String eol = System.getProperty("line.separator");
        String select = "SELECTION getName()" + eol;

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
            tvResultSet = new TimeVaryingResultSetMem(new ArrayList<Binding>(), ((RSPQuery) getQuery()).getResultVars());
        } else {

            SelectResponse remove1 = (SelectResponse) new_response;
            ResultSetRewindable resultSetRewindable = ResultSetFactory.makeRewindable(remove1.getResults());
            resultSetRewindable.reset();

            List<Binding> removeSolutionSet = getSolutionSet(resultSetRewindable);
            resultSetRewindable.reset();

            this.solutionSet.removeAll(removeSolutionSet);

            tvResultSet = new TimeVaryingResultSetMem(this.solutionSet, this.getResults().getResultVars());
        }
        return new SelectResponse(getId(), ((RSPQuery) getQuery()), tvResultSet, getCep_timestamp());
    }

    @Override
    public InstantaneousResponse intersection(InstantaneousResponse new_response) {
        TimeVaryingResultSetMem tvResultSet;
        if (new_response == null) {
            tvResultSet = new TimeVaryingResultSetMem(new ArrayList<Binding>(), ((RSPQuery) getQuery()).getResultVars());
        } else {

            SelectResponse remove1 = (SelectResponse) new_response;
            ResultSetRewindable rs = ResultSetFactory.makeRewindable(remove1.getResults());
            rs.reset();

            List<Binding> newSolutionBindings = getSolutionSet(rs);
            rs.reset();

            List<Binding> copy = new ArrayList<>(this.solutionSet);
            copy.removeAll(newSolutionBindings);

            this.solutionSet.removeAll(copy);

            tvResultSet = new TimeVaryingResultSetMem(this.solutionSet, ((RSPQuery) getQuery()).getResultVars());
        }

        return new SelectResponse(getId(), (RSPQuery) getQuery(), tvResultSet, getCep_timestamp());

    }

    private List<Binding> getSolutionSet(ResultSet results) {
        List<Binding> solutions = new ArrayList<>();
        while (results.hasNext()) {
            solutions.add(results.nextBinding());
        }
        return solutions;
    }


}
