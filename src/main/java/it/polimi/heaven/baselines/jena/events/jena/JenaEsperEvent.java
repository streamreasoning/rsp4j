package it.polimi.heaven.baselines.jena.events.jena;

import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.Set;

import com.hp.hpl.jena.graph.Graph;

public interface JenaEsperEvent {

	public Graph addTo(Graph abox);

	public Graph removeFrom(Graph abox);

	public Set<TripleContainer> serialize();

}
