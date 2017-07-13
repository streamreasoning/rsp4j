package it.polimi.jasper.engine.stream;

import it.polimi.jasper.engine.sds.InstantaneousGraph;
import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.stream.StreamItem;
import lombok.NoArgsConstructor;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor

public class GraphStimulus extends StreamItem<Graph> {

    private static final long serialVersionUID = 1L;

    public GraphStimulus(long appTimestamp1, Graph content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }

    @Override
    public InstantaneousGraph addTo(InstantaneousItem abox) {
        if (abox instanceof InstantaneousGraph) {
            InstantaneousGraph abox1 = (InstantaneousGraph) abox;
            GraphUtil.addInto(abox1, this.getTypedContent());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] addTo [" + this.getClass() + "] ");
        }
    }

    @Override
    public Set<RDFLine> serialize() {
        HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
        ExtendedIterator<Triple> all = GraphUtil.findAll(getTypedContent());
        while (all.hasNext()) {
            Triple next = all.next();
            hashSet.add(new RDFLine(next.getSubject().toString(), next.getPredicate().toString(),
                    next.getObject().toString()));
        }

        return hashSet;
    }

    @Override
    public InstantaneousGraph removeFrom(InstantaneousItem abox) {
        if (abox instanceof InstantaneousGraph) {
            InstantaneousGraph abox1 = (InstantaneousGraph) abox;
            GraphUtil.deleteFrom(abox1, getTypedContent());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] removeFrom [" + this.getClass() + "] ");
        }
    }

    @Override
    public String toString() {
        return "GraphStimulus {" + "appTimestamp='" + getAppTimestamp() + '\'' + ", sysTimestamp='" + getSysTimestamp()
                + '\'' + ", content='" + getTypedContent() + '\'' + ", stream_uri='" + getStream_uri() + '\'' + '}';
    }

    @Override
    public String getStreamURI() {
        return getStream_uri();
    }
}
