package it.polimi.rsp.baselines.rsp.query.response;

import it.polimi.services.FileService;

import java.util.List;

import it.polimi.sr.rsp.RSPQuery;
import lombok.Getter;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

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
}
