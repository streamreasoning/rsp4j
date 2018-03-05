package rspql.syntax;

import it.polimi.yasper.core.enums.StreamOperator;
import it.polimi.yasper.core.rspql.ContinuousQuery;
import it.polimi.yasper.core.rspql.SDSBuilder;
import it.polimi.yasper.core.rspql.Stream;
import it.polimi.yasper.core.spe.windowing.operator.WindowOperator;
import org.apache.jena.graph.Node;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryVisitor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RSPQLJenaQuery extends Query implements ContinuousQuery {
    public static String RSTREAM = "RSTREAM";
    public static String ISTREAM = "ISTREAM";
    public static String DSTREAM = "DSTREAM";
    public static String defaultStreamType = RSTREAM;
    private String streamType = defaultStreamType;
    private String outputStreamUri;
    private List<NamedWindow> namedWindows = new ArrayList<>();
    private List<ElementNamedWindow> elementNamedWindows = new ArrayList<>();

    public void addNamedWindow(Node windowUri, Node streamUri, Duration range, Duration step){
        NamedWindow namedWindow = new NamedWindow(this, windowUri, streamUri, NamedWindow.LOGICAL_WINDOW);
        namedWindow.setLogicalRange(range);
        namedWindow.setLogicalStep(step);
        namedWindows.add(namedWindow);
    }

    public void addNamedWindow(Node windowUri, Node streamUri, int range, int step){
        NamedWindow namedWindow = new NamedWindow(this, windowUri, streamUri, NamedWindow.PHYSICAL_WINDOW);
        namedWindow.setPhysicalRange(range);
        namedWindow.setPhysicalStep(step);
        namedWindows.add(namedWindow);
    }

    public void setIstream() { streamType = ISTREAM; }

    public void setRstream() { streamType = RSTREAM; }

    public void setDstream() { streamType = DSTREAM; }

    public void setOutputStream(String uri) { outputStreamUri = uri; }

    public String getStreamType() { return streamType; }

    public String getOutputStreamUri() { return outputStreamUri; }

    public List<NamedWindow> getNamedWindows() { return namedWindows; }

    public List<ElementNamedWindow> getElementNamedWindows() { return elementNamedWindows; }

    @Override
    public String getID() { return null; }

    @Override
    public StreamOperator getR2S() { return null; }

    @Override
    public boolean isRecursive() { return false; }

    @Override
    public Map<? extends WindowOperator, Stream> getWindowMap() { return null; }

    @Override
    public void accept(SDSBuilder v) { }


    public void visit(QueryVisitor visitor) {
        visitor.startVisit(this) ;
        visitor.visitResultForm(this) ;
        visitor.visitPrologue(this) ;
        if ( this.isSelectType() )
            visitor.visitSelectResultForm(this) ;
        if ( this.isConstructType() )
            visitor.visitConstructResultForm(this) ;
        if ( this.isDescribeType() )
            visitor.visitDescribeResultForm(this) ;
        if ( this.isAskType() )
            visitor.visitAskResultForm(this) ;
        visitor.visitDatasetDecl(this) ;
        visitor.visitQueryPattern(this) ;
        visitor.visitGroupBy(this) ;
        visitor.visitHaving(this) ;
        visitor.visitOrderBy(this) ;
        visitor.visitOffset(this) ;
        visitor.visitLimit(this) ;
        visitor.visitValues(this) ;
        visitor.finishVisit(this) ;
    }

    public void addElementNamedWindow(ElementNamedWindow elementNamedWindow) {
        elementNamedWindows.add(elementNamedWindow);
    }
}
