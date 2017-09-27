package it.polimi.jasper.engine.stream.items;

import it.polimi.jasper.engine.instantaneous.JenaGraph;
import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.query.Updatable;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;
import org.apache.jena.graph.Triple;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.util.HashSet;
import java.util.Set;

//TODO wrap rid of  JenaGraph
public class GraphStreamItem extends RDFStreamItem<JenaGraph> {

    private static final long serialVersionUID = 1L;

    public GraphStreamItem(long appTimestamp1, Graph content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }

    @Override
    public JenaGraph addTo(Updatable abox) {
        if (abox instanceof JenaGraph) {
            JenaGraph abox1 = (JenaGraph) abox;
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
    public JenaGraph removeFrom(Updatable abox) {
        if (abox instanceof JenaGraph) {
            JenaGraph abox1 = (JenaGraph) abox;
            GraphUtil.deleteFrom(abox1, getTypedContent());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] removeFrom [" + this.getClass() + "] ");
        }
    }

    @Override
    public String toString() {
        return "GraphStreamItem {" + "appTimestamp='" + getAppTimestamp() + '\'' + ", sysTimestamp='" + getSysTimestamp()
                + '\'' + ", content='" + getTypedContent() + '\'' + ", stream_uri='" + getStream_uri() + '\'' + '}';
    }

    @Override
    public String getStreamURI() {
        return getStream_uri();
    }
}
