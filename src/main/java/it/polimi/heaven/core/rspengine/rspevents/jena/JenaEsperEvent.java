package it.polimi.processing.rspengine.rspevents.jena;

import it.polimi.processing.events.TripleContainer;

import java.util.Set;

import com.hp.hpl.jena.graph.Graph;

public interface JenaEsperEvent {

	public Graph addTo(Graph abox);

	public Graph removeFrom(Graph abox);

	public Set<TripleContainer> serialize();

}
