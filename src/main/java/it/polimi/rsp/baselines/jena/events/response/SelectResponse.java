package it.polimi.rsp.baselines.jena.events.response;

import it.polimi.heaven.core.teststand.rspengine.Query;
import it.polimi.services.FileService;

import java.util.List;

import lombok.Getter;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

@Getter
public final class SelectResponse extends BaselineResponse {

	private ResultSet results;

	public SelectResponse(String id, Query query, ResultSet results) {
		super(id, System.currentTimeMillis(), query);
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
