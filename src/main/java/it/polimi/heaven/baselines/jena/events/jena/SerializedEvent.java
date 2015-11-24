package it.polimi.heaven.baselines.jena.events.jena;

import it.polimi.heaven.baselines.jena.events.SerializedTripleEvent;
import it.polimi.heaven.core.ts.events.TripleContainer;
import it.polimi.utils.RDFSUtils;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

@EqualsAndHashCode(callSuper = false)
public class SerializedEvent extends SerializedTripleEvent implements JenaEsperEvent {

	private final String channel = "toJena";

	public SerializedEvent(String s, String p, String o, long timestamp, long app_timestamp) {
		super(s, p, o, timestamp, app_timestamp);
	}

	@Override
	public String toString() {
		return "SerializedEvent [s=" + s + ", p=" + p + ", o=" + o + " ,ts=" + timestamp + " ,app_ts=" + app_timestamp + "]";
	}

	@Override
	public Graph addTo(Graph abox) {
		Resource subject = ResourceFactory.createResource(getS());
		Property predicate = (getP() != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(getP()) : RDF.type;
		RDFNode object = ResourceFactory.createResource(getO());
		abox.add(ResourceFactory.createStatement(subject, predicate, object).asTriple());
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
		Resource subject = ResourceFactory.createResource(getS());
		Property predicate = (getP() != RDFSUtils.TYPE_PROPERTY) ? ResourceFactory.createProperty(getP()) : RDF.type;
		RDFNode object = ResourceFactory.createResource(getO());
		abox.remove(subject.asNode(), predicate.asNode(), object.asNode());
		return abox;
	}
}
