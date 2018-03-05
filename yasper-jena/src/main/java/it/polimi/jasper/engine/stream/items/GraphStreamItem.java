package it.polimi.jasper.engine.stream.items;

import it.polimi.jasper.engine.instantaneous.JenaGraph;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.GraphUtil;

//TODO wrap rid of  JenaGraph
public class GraphStreamItem extends RDFStreamItem<Graph> {

    private static final long serialVersionUID = 1L;

    public GraphStreamItem(long appTimestamp1, Graph content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }

    @Override
    public JenaGraph addTo(JenaGraph abox) {
        if (abox instanceof JenaGraph) {
            JenaGraph abox1 = (JenaGraph) abox;
            GraphUtil.addInto(abox1, this.getTypedContent());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] addTo [" + this.getClass() + "] ");
        }
    }

    @Override
    public JenaGraph removeFrom(JenaGraph abox) {
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
