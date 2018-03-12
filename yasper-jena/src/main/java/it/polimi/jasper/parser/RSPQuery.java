package it.polimi.jasper.parser;

import it.polimi.jasper.parser.streams.Register;
import it.polimi.jasper.parser.streams.WindowOperatorNode;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.quering.ContinuousQuery;
import it.polimi.yasper.core.stream.Stream;
import it.polimi.yasper.core.utils.QueryConfiguration;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryException;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.syntax.ElementNamedGraph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Riccardo on 05/08/16.
 */
@Data
@Log4j
public class RSPQuery extends SPARQLQuery implements ContinuousQuery {

    private Map<Node, WindowedStreamNode> namedwindows = new HashMap<>();
    private Set<WindowedStreamNode> windows = new HashSet<>();
    private List<ElementNamedGraph> windowGraphElements;
    private Register header;
    private StreamOperator r2s;
    @Getter
    private boolean recursive;

    @Setter
    @Getter
    private QueryConfiguration configuration;

    public RSPQuery(Prologue prologue) {
        query.usePrologueFrom(prologue);
    }

    public RSPQuery() {
        query.setBaseURI(IRIResolver.createNoResolve());
    }

    public RSPQuery(IRIResolver r) {
        super(r);
    }


    public RSPQuery setSelectQuery() {
        query.setQuerySelectType();
        return this;
    }

    public RSPQuery setConstructQuery(StreamOperator r2s) {
        this.r2s = r2s;
        query.setQueryConstructType();
        return this;
    }

    public String getName() {
        if (header != null) {
            return header.getId().getURI();
        }
        String id = hashCode() + "";
        this.header = new Register().setId(NodeFactory.createURI(id));
        return id;
    }

    public Query getQ() {
        return query;
    }

    public RSPQuery addNamedWindow(WindowedStreamNode nw) {
        if (namedwindows.containsKey(nw.getIri()))
            throw new QueryException("WindowedStreamNode [" + nw.getIri() +
                    " ] already opened on a stream: " + namedwindows.get(nw.getIri()));


        addNamedGraphURI(nw.getIri());
        namedwindows.put(nw.getIri(), nw);
        return this;
    }

    public RSPQuery addWindow(WindowedStreamNode w) {

        if (!isRecursive() && w.getStreamURI().equals(getID())) {
            setRecursive(true);
        }

        if (w.isNamed()) {
            return addNamedWindow(w);
        }


        if (windows.contains(w))
            throw new QueryException("WindowedStreamNode already opened on default stream: " + w.getStream().getIri());

        addGraphURI(w.getStream().getIri());
        windows.add(w);
        return this;

    }

    public RSPQuery addElement(ElementNamedGraph elm) {
        if (windowGraphElements == null) {
            windowGraphElements = new ArrayList<>();
        }
        windowGraphElements.add(elm);
        return this;
    }

    public RSPQuery setRegister(Register register) {
        this.header = register;
        return this;
    }

    public List<String> getNamedGraphURIs() {
        return query.getNamedGraphURIs();
    }

    @Override
    public String getSPARQL() {
        return query.toString();
    }


    public List<String> getGraphURIs() {
        return query.getGraphURIs();
    }

    @Override
    public List<String> getNamedwindowsURIs() {
        return namedwindows.keySet().stream().map(Node::getURI).collect(Collectors.toList());
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
        for (WindowedStreamNode w : windows) {
            res &= !w.getIri().equals(blankNode);
        }
        return !res;
    }


    @Override
    public String getID() {
        return getName();
    }

    @Override
    public StreamOperator getR2S() {
        return r2s;
    }

    @Override
    public Map<WindowOperatorNode, Stream> getWindowMap() {
        Map<WindowOperatorNode, Stream> map = new HashMap<>();
        getWindows().stream().forEach(w -> map.put(w, w.getStream()));
        namedwindows.entrySet().stream().forEach(e -> map.put(e.getValue(), e.getValue().getStream()));
        return map;
    }

    @Override
    public boolean isSelectType() {
        return query.isSelectType();
    }

    @Override
    public boolean isConstructType() {
        return query.isConstructType();
    }

    @Override
    public int getQueryType() {
        return query.getQueryType();
    }

    public IRIResolver getResolver() {
        return query.getResolver();
    }

    @Override
    public String toString() {
        return query.toString();
    }
}