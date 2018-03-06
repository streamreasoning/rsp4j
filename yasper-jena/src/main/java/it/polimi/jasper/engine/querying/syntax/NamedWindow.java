package it.polimi.jasper.engine.querying.syntax;

import org.apache.jena.graph.Node;

import java.time.Duration;

public class NamedWindow {
    public static int LOGICAL_WINDOW = 0;
    public static int PHYSICAL_WINDOW = 1;
    public int windowType;
    private Node windowUri;
    private Node streamUri;
    private Duration logicalRange;
    private Duration logicalStep;
    private int physicalRange;
    private int physicalStep;
    private RSPQLJenaQuery query;

    public NamedWindow(RSPQLJenaQuery query, Node windowUri, Node streamUri, int windowType){
        this.query = query;
        this.windowUri = windowUri;
        this.streamUri = streamUri;
        this.windowType = windowType;
    }

    public Node getWindowUri(){
        return windowUri;
    }

    public Node getStreamUri(){
        return streamUri;
    }

    public void setLogicalRange(Duration range) {
        logicalRange = range;
    }

    public void setLogicalStep(Duration step) {
        logicalStep = step;
    }

    public void setPhysicalRange(int range) {
        physicalRange = range;
    }

    public void setPhysicalStep(int step) {
        physicalStep = step;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("FROM NAMED WINDOW ");
        String w = windowUri.toString();
        String wShort = query.getPrefixMapping().qnameFor(w);
        if(wShort != null){
            sb.append(String.format("%s ", wShort));
        } else {
            sb.append(String.format("<%s> ", w));
        }
        String s = streamUri.toString();
        String sShort = query.getPrefixMapping().qnameFor(s);
        if(sShort != null){
            sb.append(String.format("%s ", sShort));
        } else {
            sb.append(String.format("<%s> ", s));
        }

        return sb.toString();
    }
}
