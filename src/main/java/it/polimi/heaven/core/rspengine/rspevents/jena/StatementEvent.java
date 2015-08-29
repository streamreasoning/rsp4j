package it.polimi.heaven.core.rspengine.rspevents.jena;

import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

@Data
@AllArgsConstructor
public class StatementEvent implements JenaEsperEvent {

	private Statement statement;
	private long appTimestamp;
	private long timestamp;

	public Resource getS() {
		return statement.getSubject();
	}

	public Property getP() {
		return statement.getPredicate();
	}

	public RDFNode getO() {
		return statement.getObject();
	}

	@Override
	public Graph addTo(Graph abox) {
		abox.add(statement.asTriple());
		return abox;
	}

	@Override
	public Set<TripleContainer> serialize() {
		HashSet<TripleContainer> hashSet = new HashSet<TripleContainer>();
		hashSet.add(new TripleContainer(getS().toString(), getP().toString(), getO().toString()));
		return hashSet;
	}

	@Override
	public Graph removeFrom(Graph abox) {
		abox.remove(statement.getSubject().asNode(), statement.getPredicate().asNode(), statement.getObject().asNode());
		return abox;
	}
}
