package it.polimi.heaven.baselines.jena.query;

import it.polimi.heaven.core.teststand.rspengine.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hp.hpl.jena.rdf.model.Model;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaselineQuery implements Query {

	private String id;
	private String esper_queries;
	private String sparql_query;
	private String[] esperStreams;

	private Model tbox;

	public boolean hasTBox() {
		return tbox != null;
	}

}
