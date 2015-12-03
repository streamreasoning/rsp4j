package it.polimi.heaven.baselines.jena;

import it.polimi.heaven.core.ts.rspengine.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.hp.hpl.jena.rdf.model.Model;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaselineQuery implements Query {

	private String[] esperStreams;
	private String esperQuery;
	private String sparqlQuery;
	private Model tbox;

	public boolean hasTBox() {
		return tbox != null;
	}

}
