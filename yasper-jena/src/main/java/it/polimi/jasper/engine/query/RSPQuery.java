package it.polimi.jasper.engine.query;

import it.polimi.jasper.parser.SPARQLQuery;
import it.polimi.jasper.parser.streams.Register;
import it.polimi.jasper.parser.streams.WindowedStreamNode;
import it.polimi.rspql.SDSBuilder;
import it.polimi.rspql.Stream;
import it.polimi.rspql.cql.s2_.WindowOperator;
import it.polimi.rspql.querying.ContinuousQuery;
import it.polimi.yasper.core.enums.StreamOperator;
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

    private Map<Node, WindowedStreamNode> namedwindows = new HashMap<Node, WindowedStreamNode>();
    private Set<WindowedStreamNode> windows = new HashSet<WindowedStreamNode>();
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
            windowGraphElements = new ArrayList<ElementNamedGraph>();
        }
        windowGraphElements.add(elm);
        return this;
    }

    public RSPQuery setRegister(Register register) {
        this.header = register;
        return this;
    }

    public List<String> getNamedGraphURIs() {
        return new ArrayList<String>();
    }

    public List<String> getGraphURIs() {
        return new ArrayList<String>();
    }

    public List<String> getRSPNamedGraphURIs() {
        return query.getNamedGraphURIs();
    }

    public List<String> getRSPGraphURIs() {
        return query.getGraphURIs();
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

    @Override
    public Set<? extends WindowOperator> getWindowsSet() {
        return windows != null ? this.windows : new HashSet<>();
    }

    @Override
    public Set<? extends WindowOperator> getNamedWindowsSet() {
        return namedwindows != null ? namedwindows.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toSet()) : new HashSet<>();
    }

    @Override
    public Map<WindowOperator, Stream> getWindowMap() {
        Map<WindowOperator, Stream> map = new HashMap<>();
        getWindows().stream().forEach(w -> map.put(w, w.getStream()));
        namedwindows.entrySet().stream().forEach(e -> map.put(e.getValue(), e.getValue().getStream()));
        return map;
    }

    @Override
    public Set<Stream> getStreamSet() {
        Set<Stream> collect = namedwindows.entrySet().stream().map(e -> e.getValue().getStream()).collect(Collectors.toSet());
        windows.stream().forEach(w -> collect.add(w.getStream()));
        return collect;
    }


    @Override
    public void accept(SDSBuilder v) {
        v.visit(this);
    }

    public IRIResolver getResolver() {
        return query.getResolver();
    }
}