package it.polimi.rsp.baselines.jena.events.stimuli;

import it.polimi.heaven.core.teststand.data.RDFLine;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.HashSet;
import java.util.Set;

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

}
