package it.polimi.processing.rspengine.rspevents.jena;

import it.polimi.processing.events.TripleContainer;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

@Data
@AllArgsConstructor
public class GraphEvent implements JenaEsperEvent {

	private Graph graph;
	private long appTimestamp;
	private long timestamp;

	@Override
	public Graph addTo(Graph abox) {
		GraphUtil.addInto(abox, graph);
		// Union union = new Union(abox, graph);
		return abox;
	}

	@Override
	public Set<TripleContainer> serialize() {
		HashSet<TripleContainer> hashSet = new HashSet<TripleContainer>();
		ExtendedIterator<Triple> all = GraphUtil.findAll(graph);
		while (all.hasNext()) {
			Triple next = all.next();
			hashSet.add(new TripleContainer(next.getSubject().toString(), next.getPredicate().toString(), next.getObject().toString()));
		}

		return hashSet;
	}

	@Override
	public Graph removeFrom(Graph abox) {
		GraphUtil.deleteFrom(abox, graph);
		return abox;
	}
}
