package it.polimi.heaven.baselines.jena.query;

import it.polimi.heaven.core.teststand.rspengine.Query;

import java.util.Arrays;

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

	@Override
	public String toString() {
		return "BaselineQuery [id=" + id + ", esper_queries=" + esper_queries + ", sparql_query=" + sparql_query + ", esperStreams="
				+ Arrays.toString(esperStreams) + "] ";
		// TODO tbox file or uri
	}

}
