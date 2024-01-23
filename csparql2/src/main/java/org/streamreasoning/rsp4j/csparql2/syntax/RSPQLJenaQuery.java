package org.streamreasoning.rsp4j.csparql2.syntax;


import lombok.Getter;
import lombok.Setter;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.irix.IRIs;
import org.apache.jena.irix.IRIx;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryVisitor;
import org.apache.jena.query.Syntax;
import org.streamreasoning.rsp4j.api.enums.StreamOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RSPQLJenaQuery extends Query implements ContinuousQuery {
    public static String RSTREAM = "RSTREAM";
    public static String ISTREAM = "ISTREAM";
    public static String DSTREAM = "DSTREAM";
    public static String defaultStreamType = RSTREAM;
    private IRIx stream_resolver;

    private String streamType = defaultStreamType;
    private String outputStreamUri;
    private List<NamedWindow> namedWindows = new ArrayList<>();
    private List<ElementNamedWindow> elementNamedWindows = new ArrayList<>();
    private Map<WindowNode, DataStream> windowMap = new HashMap<>();

    @Getter
    @Setter
    private SDSConfiguration configuration;

    public RSPQLJenaQuery() {
    }

    @Override
    public boolean isSelectType() {
        return true;
    }

    public RSPQLJenaQuery(String baseuri) {
        this.stream_resolver = IRIx.create(baseuri + "/streams/");
    }

    public void addNamedWindow(Node windowUri, Node streamUri, Duration range, Duration step) {
        streamUri = NodeFactory.createURI(IRIs.resolve(streamUri.getURI()));
        NamedWindow namedWindow = new NamedWindow(this, windowUri, streamUri, NamedWindow.LOGICAL_WINDOW);
        namedWindow.setLogicalRange(range);
        namedWindow.setLogicalStep(step);
        namedWindows.add(namedWindow);
        windowMap.put(namedWindow, new StreamNode(namedWindow.getStreamUri()));
    }

    public void addNamedWindow(Node windowUri, Node streamUri, int range, int step) {
        streamUri = NodeFactory.createURI(IRIs.resolve(streamUri.getURI()));
        NamedWindow namedWindow = new NamedWindow(this, windowUri, streamUri, NamedWindow.PHYSICAL_WINDOW);
        namedWindow.setPhysicalRange(range);
        namedWindow.setPhysicalStep(step);
        namedWindows.add(namedWindow);
        windowMap.put(namedWindow, new StreamNode(namedWindow.getStreamUri()));
    }

    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        StreamNode value = new StreamNode(NodeFactory.createURI(streamUri));
        windowMap.put(wo, value);
    }

    public void setIstream() {
        streamType = ISTREAM;
    }

    public void setRstream() {
        streamType = RSTREAM;
    }

    public void setDstream() {
        streamType = DSTREAM;
    }

    @Override
    public boolean isIstream() {
        return ISTREAM.equals(streamType);
    }

    @Override
    public boolean isRstream() {
        return RSTREAM.equals(streamType);
    }

    @Override
    public boolean isDstream() {
        return DSTREAM.equals(streamType);
    }

    @Override
    public void setSelect() {

    }

    @Override
    public void setConstruct() {

    }

    public void setOutputStream(String uri) {
        outputStreamUri = uri;
    }

    @Override
    public DataStream getOutputStream() {
        return new DataStreamImpl(outputStreamUri);
    }

    public String getStreamType() {
        return streamType;
    }

    public String getOutputStreamUri() {
        return outputStreamUri;
    }

    public List<NamedWindow> getNamedWindows() {
        return namedWindows;
    }

    public List<ElementNamedWindow> getElementNamedWindows() {
        return elementNamedWindows;
    }

    @Override
    public String getID() {
        return null;
    }

    public StreamOperator getR2S() {
        return StreamOperator.valueOf(streamType);
    }

    public boolean isRecursive() {
        return false;
    }

    @Override
    public Map<WindowNode, DataStream> getWindowMap() {
        return windowMap;
    }

    public List<String> getNamedwindowsURIs() {
        return getNamedWindows().stream().map(NamedWindow::getWindowUri).map(Node::getURI).collect(Collectors.toList());
    }

    public String getSPARQL() {
        return ((Query) this).serialize(Syntax.syntaxARQ);
    }

    @Override
    public Time getTime() {
        return null;
    }

    @Override
    public RelationToRelationOperator r2r() {
        return null;
    }

    @Override
    public StreamToRelationOp[] s2r() {
        return new StreamToRelationOp[0];
    }

    @Override
    public RelationToStreamOperator r2s() {
        return null;
    }

    @Override
    public List<Aggregation> getAggregations() {
        return null;
    }


    public void visit(QueryVisitor visitor) {
        visitor.startVisit(this);
        visitor.visitResultForm(this);
        visitor.visitPrologue(this);
        if (this.isSelectType())
            visitor.visitSelectResultForm(this);
        if (this.isConstructType())
            visitor.visitConstructResultForm(this);
        if (this.isDescribeType())
            visitor.visitDescribeResultForm(this);
        if (this.isAskType())
            visitor.visitAskResultForm(this);
        visitor.visitDatasetDecl(this);
        visitor.visitQueryPattern(this);
        visitor.visitGroupBy(this);
        visitor.visitHaving(this);
        visitor.visitOrderBy(this);
        visitor.visitOffset(this);
        visitor.visitLimit(this);
        visitor.visitValues(this);
        visitor.finishVisit(this);
    }

    public void addElementNamedWindow(ElementNamedWindow elementNamedWindow) {
        elementNamedWindows.add(elementNamedWindow);
    }
}
