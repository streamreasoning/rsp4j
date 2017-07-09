package it.polimi.yasper.core.jena.stream;

import it.polimi.rdf.RDFLine;
import it.polimi.yasper.core.jena.JenaTimeVaryingGraph;
import it.polimi.yasper.core.stream.StreamItem;
import lombok.NoArgsConstructor;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class StatementStimulus extends StreamItem {

    private static final long serialVersionUID = 1L;

    public StatementStimulus(long appTimestamp1, Statement content1, String stream_uri) {
        super(appTimestamp1, content1, stream_uri);
    }

    public Statement getContent() {
        return (Statement) super.get(content);
    }

    public Resource getS() {
        return getContent().getSubject();
    }

    public Property getP() {
        return getContent().getPredicate();
    }

    public RDFNode getO() {
        return getContent().getObject();
    }

    @Override
    public JenaTimeVaryingGraph addTo(JenaTimeVaryingGraph abox) {
        abox.add(getContent().asTriple());
        return abox;
    }

    @Override
    public Set<RDFLine> serialize() {
        HashSet<RDFLine> hashSet = new HashSet<RDFLine>();
        hashSet.add(new RDFLine(getS().toString(), getP().toString(), getO().toString()));
        return hashSet;
    }

    @Override
    public JenaTimeVaryingGraph removeFrom(JenaTimeVaryingGraph abox) {
        abox.remove(getS().asNode(), getP().asNode(), getO().asNode());
        return abox;
    }


    @Override
    public String toString() {
        return "StatementStimulus {" + "appTimestamp='" + getAppTimestamp() + '\'' + ", sysTimestamp='" + getSysTimestamp()
                + '\'' + ", content='" + getContent() + '\'' + ", stream_uri='" + getStream_uri() + '\'' + '}';
    }

    @Override
    public String getStreamURI() {
        return getStream_uri();
    }
}