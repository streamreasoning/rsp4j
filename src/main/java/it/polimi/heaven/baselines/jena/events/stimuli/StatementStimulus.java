package it.polimi.heaven.baselines.jena.events.stimuli;

import it.polimi.heaven.core.teststand.data.RDFLine;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

public class StatementStimulus extends BaselineStimulus {

	private static final long serialVersionUID = 1L;

	public StatementStimulus() {
		super(Statement.class);
	}

	public StatementStimulus(long appTimestamp1, long sysTimestamp1, Statement content1, String stream_name) {
		super(appTimestamp1, sysTimestamp1, content1, stream_name);
	}

	public Statement getContent() {
		return (Statement) super.getContent();
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
	public Set<RDFLine> serialize() {
		HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
		hashSet.add(new RDFLine(getS().toString(), getP().toString(), getO().toString()));
		return hashSet;
	}

	@Override
	public Graph removeFrom(Graph abox) {
		abox.remove(getS().asNode(), getP().asNode(), getO().asNode());
		return abox;
	}

	@Override
	public String toString() {
		return "StatementStimulus on Stream [" + getStream_name() + "] [" + super.toString() + "]";
	}
}
