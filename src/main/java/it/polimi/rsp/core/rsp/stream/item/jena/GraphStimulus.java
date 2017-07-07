package it.polimi.rsp.core.rsp.stream.item.jena;

import it.polimi.rdf.RDFLine;
import it.polimi.rsp.core.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.core.rsp.stream.item.StreamItem;
import lombok.NoArgsConstructor;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor

public class GraphStimulus extends StreamItem {

	private static final long serialVersionUID = 1L;

	public GraphStimulus(long appTimestamp1, Graph content1, String stream_uri) {
		super(appTimestamp1, content1, stream_uri);
	}

	public Graph getContent() {
		return (Graph) super.get(content);
	}

	@Override
	public TimeVaryingGraph addTo(TimeVaryingGraph abox) {
		GraphUtil.addInto(abox, this.getContent());
		return abox;
	}

	@Override
	public Set<RDFLine> serialize() {
		HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
		ExtendedIterator<Triple> all = GraphUtil.findAll(getContent());
		while (all.hasNext()) {
			Triple next = all.next();
			hashSet.add(new RDFLine(next.getSubject().toString(), next.getPredicate().toString(),
					next.getObject().toString()));
		}

		return hashSet;
	}

	@Override
	public TimeVaryingGraph removeFrom(TimeVaryingGraph abox) {
		GraphUtil.deleteFrom(abox, getContent());
		return abox;
	}

	@Override
	public String toString() {
		return "GraphStimulus {" + "appTimestamp='" + getAppTimestamp() + '\'' + ", sysTimestamp='" + getSysTimestamp()
				+ '\'' + ", content='" + getContent() + '\'' + ", stream_uri='" + getStream_uri() + '\'' + '}';
	}

	@Override
	public String getStreamURI() {
		return getStream_uri();
	}
}
