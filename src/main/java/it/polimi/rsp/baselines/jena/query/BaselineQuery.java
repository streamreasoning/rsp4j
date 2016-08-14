package it.polimi.rsp.baselines.jena.query;


import java.util.Arrays;

import it.polimi.heaven.core.teststand.rsp.querying.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.jena.rdf.model.Model;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaselineQuery implements Query {

	private String id;
	private String esper_queries;
	private String sparql_query;
	private String[] esperNamedStreams;
	private String[] esperStreams;

	private Model tbox;

	public boolean hasTBox() {
		return tbox != null;
	}

	@Override
	public String toString() {
		return "BaselineQuery [id=" + id + ", esper_queries=" + esper_queries + ", sparql_query=" + sparql_query + ", esperStreams="
				+ Arrays.toString(esperStreams) + "] ";
		// TODO tbox file or uri
	}

}
