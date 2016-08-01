package it.polimi.heaven.baselines.jena.events.response;

import it.polimi.heaven.core.teststand.rspengine.Query;
import it.polimi.services.FileService;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@Getter
@Log4j
public final class ConstructResponse extends BaselineResponse {
	private Model results;

	public ConstructResponse(String id, Query query, Model results) {
		super(id, System.currentTimeMillis(), query);
		this.results = results;
	}

	@Override
	public boolean save(String where) {
		log.debug("Save Data [" + where + "]");

		return FileService.write(where + ".trig", getData());
	}

	private String getData() {

		String eol = System.getProperty("line.separator");
		String trig = getId() + " {";
		StmtIterator listStatements = results.listStatements();
		while (listStatements.hasNext()) {
			Statement s = listStatements.next();
			trig += eol + "<" + s.getSubject().toString() + ">" + " " + "<" + s.getPredicate().toString() + ">" + " " + "<"
					+ s.getObject().toString() + "> .";
		}
		trig += eol + "}" + eol;
		return trig;
	}
}
