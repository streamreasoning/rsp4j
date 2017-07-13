package it.polimi.jasper.engine.stream;

import it.polimi.jasper.engine.sds.InstantaneousGraph;
import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.query.InstantaneousItem;
import it.polimi.yasper.core.stream.StreamItem;
import lombok.NoArgsConstructor;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class StatementStimulus extends StreamItem<Statement> {

    private static final long serialVersionUID = 1L;

    public StatementStimulus(long appTimestamp1, Statement content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }

    public Resource getS() {
        return getTypedContent().getSubject();
    }

    public Property getP() {
        return getTypedContent().getPredicate();
    }

    public RDFNode getO() {
        return getTypedContent().getObject();
    }

    @Override
    public InstantaneousGraph addTo(InstantaneousItem abox) {
        //abox.addContent(this);
        if (abox instanceof InstantaneousGraph) {
            InstantaneousGraph abox1 = (InstantaneousGraph) abox;
            abox1.add(getTypedContent().asTriple());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] addTo [" + this.getClass() + "] ");
        }
    }

    @Override
    public Set<RDFLine> serialize() {
        HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
        hashSet.add(new RDFLine(getS().toString(), getP().toString(), getO().toString()));
        return hashSet;
    }

    @Override
    public InstantaneousGraph removeFrom(InstantaneousItem abox) {
        // abox.removeContent(this);
        if (abox instanceof InstantaneousGraph) {
            InstantaneousGraph abox1 = (InstantaneousGraph) abox;
            abox1.remove(getS().asNode(), getP().asNode(), getO().asNode());
            return abox1;
        } else {
            throw new UnsupportedOperationException("[" + abox.getClass() + "] removeFrom [" + this.getClass() + "] ");
        }

    }


    @Override
    public String toString() {
        return "StatementStimulus {" + "appTimestamp='" + getAppTimestamp() + '\'' + ", sysTimestamp='" + getSysTimestamp()
                + '\'' + ", content='" + getTypedContent() + '\'' + ", stream_uri='" + getStream_uri() + '\'' + '}';
    }

    @Override
    public String getStreamURI() {
        return getStream_uri();
    }
}
