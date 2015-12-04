package it.polimi.heaven.baselines.jena.events;

import it.polimi.heaven.core.ts.events.TripleContainer;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GraphEvent extends BaselineEvent {

	public GraphEvent() {
		super(Graph.class);
	}

	public GraphEvent(long appTimestamp1, long sysTimestamp1, Graph content1) {
		super(appTimestamp1, sysTimestamp1, content1);
	}

	private static final long serialVersionUID = 1L;

	public Graph getContent() {
		return (Graph) super.get(super.content);
	}

	@Override
	public Graph addTo(Graph abox) {
		GraphUtil.addInto(abox, getContent());
		return abox;
	}

	@Override
	public Set<TripleContainer> serialize() {
		HashSet<TripleContainer> hashSet = new HashSet<TripleContainer>();
		ExtendedIterator<Triple> all = GraphUtil.findAll(getContent());
		while (all.hasNext()) {
			Triple next = all.next();
			hashSet.add(new TripleContainer(next.getSubject().toString(), next.getPredicate().toString(), next.getObject().toString()));
		}

		return hashSet;
	}

	@Override
	public Graph removeFrom(Graph abox) {
		GraphUtil.deleteFrom(abox, getContent());
		return abox;
	}

	@Override
	public String toString() {
		return "GraphEvent [" + super.toString() + "]";
	}
}
