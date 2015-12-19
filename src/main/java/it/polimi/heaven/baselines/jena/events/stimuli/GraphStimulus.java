package it.polimi.heaven.baselines.jena.events.stimuli;

import it.polimi.heaven.core.teststand.data.RDFLine;

import java.util.HashSet;
import java.util.Set;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphUtil;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GraphStimulus extends BaselineStimulus {

	private static final long serialVersionUID = 1L;

	public GraphStimulus() {
		super(Graph.class);
	}

	public GraphStimulus(long appTimestamp1, Graph content1, String stream_name) {
		super(appTimestamp1, content1, stream_name);
	}

	public Graph getContent() {
		return (Graph) super.get(content);
	}

	@Override
	public Graph addTo(Graph abox) {
		GraphUtil.addInto(abox, this.getContent());
		return abox;
	}

	@Override
	public Set<RDFLine> serialize() {
		HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
		ExtendedIterator<Triple> all = GraphUtil.findAll(getContent());
		while (all.hasNext()) {
			Triple next = all.next();
			hashSet.add(new RDFLine(next.getSubject().toString(), next.getPredicate().toString(), next.getObject().toString()));
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
		return "GraphStimulus on Stream [" + getStream_name() + "] [" + super.toString() + "]";
	}

}
