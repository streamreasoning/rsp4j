package it.polimi.heaven.baselines.jena.events;

import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class StatementEvent extends BaselineEvent {

	private static final long serialVersionUID = 1L;

	public StatementEvent() {
		super(Statement.class);
	}

	public StatementEvent(long appTimestamp1, long sysTimestamp1, Statement content1) {
		super(appTimestamp1, sysTimestamp1, content1);
	}

	public Statement getContent() {
		return (Statement) super.get(super.content);
	}

	public Resource getS() {
		return getContent().getSubject();
	}

	public Property getP() {
		return getContent().getPredicate();
	}

	public RDFNode getO() {
		return getContent().getObject();
	}

	@Override
	public Graph addTo(Graph abox) {
		abox.add(getContent().asTriple());
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
		abox.remove(getS().asNode(), getP().asNode(), getO().asNode());
		return abox;
	}
}
