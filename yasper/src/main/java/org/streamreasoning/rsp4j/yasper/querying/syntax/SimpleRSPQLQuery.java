package org.streamreasoning.rsp4j.yasper.querying.syntax;


import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.enums.StreamOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.api.stream.web.WebStream;
import org.streamreasoning.rsp4j.api.stream.web.WebStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SimpleRSPQLQuery<O> implements RSPQL<O> {

    private String id;

    private WebStream outputStream;
    private VarOrTerm s, p, o;

    private Map<WindowNode, WebStream> windowMap = new HashMap<>();
    private List<String> graphURIs = new ArrayList<>();
    private List<String> namedwindowsURIs = new ArrayList<>();
    private List<String> namedGraphURIs = new ArrayList<>();

    private StreamOperator streamOperator;
    public SimpleRSPQLQuery(String id, WebStream stream, WindowNode win, VarOrTerm s, VarOrTerm p, VarOrTerm o) {
        this.id = id;
        this.outputStream = new WebStreamImpl(id);
        this.s = s;
        this.p = p;
        this.o = o;
        windowMap.put(win, stream);
    }

    public SimpleRSPQLQuery(String id) {
        this.id = id;
    }


    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        WebStream s = new WebStreamImpl(streamUri);
        windowMap.put(wo, s);
    }

    @Override
    public void setIstream() {
        streamOperator = StreamOperator.ISTREAM;
    }

    @Override
    public void setRstream() {
        streamOperator = StreamOperator.RSTREAM;
    }

    @Override
    public void setDstream() {
        streamOperator = StreamOperator.DSTREAM;
    }

    @Override
    public boolean isIstream() {
        return streamOperator.equals(StreamOperator.ISTREAM);
    }

    @Override
    public boolean isRstream() {
        return streamOperator.equals(StreamOperator.RSTREAM);
    }

    @Override
    public boolean isDstream() {
        return streamOperator.equals(StreamOperator.DSTREAM);
    }

    @Override
    public void setSelect() {

    }

    @Override
    public void setConstruct() {

    }

    @Override
    public boolean isSelectType() {
        return false;
    }

    @Override
    public boolean isConstructType() {
        return false;
    }

    @Override
    public void setOutputStream(String uri) {
        this.outputStream = new WebStreamImpl(uri);
    }

    @Override
    public WebStream getOutputStream() {
        return outputStream;
    }

    @Override
    public String getID() {
        return id;
    }


    @Override
    public Map<WindowNode, WebStream> getWindowMap() {
        return windowMap;
    }


    @Override
    public Time getTime() {
        return TimeFactory.getInstance();
    }

    @Override
    public RelationToRelationOperator<Graph, Binding> r2r() {
        return new TP(s, p, o);
    }

    @Override
    public StreamToRelationOp<Graph, Graph>[] s2r() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToStreamOperator<O> r2s() {
        return new Rstream();
    }
}
