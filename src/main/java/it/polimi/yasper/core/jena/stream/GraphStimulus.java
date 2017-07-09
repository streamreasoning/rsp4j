package it.polimi.yasper.core.jena.stream;

import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.jena.JenaTimeVaryingGraph;
import it.polimi.yasper.core.stream.StreamItem;
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
    public JenaTimeVaryingGraph addTo(JenaTimeVaryingGraph abox) {
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
    public JenaTimeVaryingGraph removeFrom(JenaTimeVaryingGraph abox) {
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
