package it.polimi.jasper.engine.query;

import it.polimi.jasper.parser.SPARQLQuery;
import it.polimi.jasper.parser.streams.Register;
import it.polimi.jasper.parser.streams.Window;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.query.ContinuousQuery;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryException;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.syntax.ElementNamedGraph;

import java.util.*;

/**
 * Created by Riccardo on 05/08/16.
 */
@Data
@NoArgsConstructor
public class RSPQuery extends SPARQLQuery implements ContinuousQuery {

    private Map<Node, Window> namedwindows;
    private Set<Window> windows;
    private List<ElementNamedGraph> windowGraphElements;
    private Register header;
    private StreamOperator r2s;

    public RSPQuery(Prologue prologue) {
        super(prologue);
    }

    public RSPQuery setSelectQuery() {
        setQuerySelectType();
        return this;
    }

    public RSPQuery setConstructQuery(StreamOperator r2s) {
        this.r2s = r2s;
        setQueryConstructType();
        return this;
    }

    public String getName() {
        if (header != null) {
            return header.getId();
        }
        String id = hashCode() + "";
        this.header = new Register().setId(id);
        return id;
    }

    public Query getQ() {
        return this;
    }

    public RSPQuery addNamedWindow(Window nw) {
        if (namedwindows == null)
            namedwindows = new HashMap<Node, Window>();
        if (namedwindows.containsKey(nw.getIri()))
            throw new QueryException("Window [" + nw.getIri() +
                    " ] already opened on a stream: " + namedwindows.get(nw.getIri()));

        addNamedGraphURI(nw.getIri());
        namedwindows.put(nw.getIri(), nw);
        return this;
    }

    public RSPQuery addWindow(Window w) {

        if (w.isNamed()) {
            return addNamedWindow(w);
        }

        if (windows == null)
            windows = new HashSet<Window>();
        if (windows.contains(w))
            throw new QueryException("Window already opened on default stream: " + w.getStream().getIri());

        addGraphURI(w.getStream().getIri());
        windows.add(w);
        return this;
    }

    public RSPQuery addElement(ElementNamedGraph elm) {
        if (windowGraphElements == null) {
            windowGraphElements = new ArrayList<ElementNamedGraph>();
        }
        windowGraphElements.add(elm);
        return this;
    }

    public RSPQuery setRegister(Register register) {
        this.header = register;
        return this;
    }

    @Override
    public List<String> getNamedGraphURIs() {
        return new ArrayList<String>();
    }

    @Override
    public List<String> getGraphURIs() {
        return new ArrayList<String>();
    }

    public List<String> getRSPNamedGraphURIs() {
        return super.getNamedGraphURIs();
    }

    public List<String> getRSPGraphURIs() {
        return super.getGraphURIs();
    }

    /**
     * Test whether the query mentions a URI for a named window.
     *
     * @param uri
     * @return True if the URI used in a FROM NAMED WINDOW clause
     */
    public boolean usesNamedWindowURI(String uri) {
        if (namedwindows != null && !namedwindows.isEmpty()) {
            return namedwindows.containsKey(NodeFactory.createURI(uri));
        }
        return false;
    }

    /**
     * Test whether the query mentions a URI for a window.
     *
     * @param uri
     * @return True if the URI used in a FROM WINDOW clause
     */
    public boolean usesWindowURI(String uri) {
        Node blankNode = NodeFactory.createBlankNode(uri);
        boolean res = true;
        for (Window w : windows) {
            res &= !w.getIri().equals(blankNode);
        }
        return !res;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getID() {
        return getName();
    }

    @Override
    public StreamOperator getR2S() {
        return r2s;
    }
}