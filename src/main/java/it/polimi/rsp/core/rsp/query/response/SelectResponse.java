package it.polimi.rsp.core.rsp.query.response;

import it.polimi.services.FileService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.sr.rsp.RSPQuery;
import lombok.Getter;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.ResultSetStream;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.util.ResultSetUtils;

@Getter
public final class SelectResponse extends InstantaneousResponse {

    private ResultSet results;

    public SelectResponse(String id, RSPQuery query, ResultSet results, long cep_timestamp) {
        super(id, System.currentTimeMillis(), cep_timestamp, query);
        this.results = results;
    }

    @Override
    public boolean save(String where) {
        return FileService.write(where + ".select", getData());
    }

    private String getData() {

        String eol = System.getProperty("line.separator");
        String select = "SELECTION getId()" + eol;

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
    public InstantaneousResponse minus(InstantaneousResponse r) {
        SelectResponse r1 = (SelectResponse) r;
        ResultSet res = r1.getResults();
        Set<QuerySolution> query_solutions = new HashSet<QuerySolution>();
        while (res.hasNext()) {
            QuerySolution qs = this.results.next();
            query_solutions.add(qs);
        }
        ResultSetFactory.makeRewindable(this.results);

        while (results.hasNext()) {
        }
        return r;

    }
}
