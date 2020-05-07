package it.polimi.csparql2.jena.syntax;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementVisitor;
import org.apache.jena.sparql.util.NodeIsomorphismMap;

/**
 * This is a placeholder class to show how an ElementNamedWindow might be included in the Jena structure.
 * Note: The current version of the RSPQLJenaQuery simply places ElementNamedWindows in a list. To actually addObservable it
 * to the Jena element structure edit the RSPQLJenaVisitor class.
 */

public class ElementNamedWindow extends Element { //extends ElementNamedGraph {
    private Node sourceNode ;
    private Element element ;


    // WINDOW <uri> or WINDOW ?var
    public ElementNamedWindow(Node n, Element el) {
        //super(n, el);
        sourceNode = n;
        element = el;
    }

    public Node getWindowNameNode() { return sourceNode ; }

    public Element getElement() {
        return element ;
    }

    @Override
    public int hashCode() { return element.hashCode() ^ sourceNode.hashCode() ; }

    @Override
    public boolean equalTo(Element el2, NodeIsomorphismMap isoMap)
    {
        if ( el2 == null ) return false ;

        if ( ! ( el2 instanceof ElementNamedWindow ) )
            return false ;
        ElementNamedWindow g2 = (ElementNamedWindow) el2 ;
        if ( ! this.getWindowNameNode().equals(g2.getWindowNameNode()) )
            return false ;
        if ( ! this.getElement().equalTo(g2.getElement(), isoMap) )
            return false ;
        return true ;
    }

    /**
     * ElementVisitor has not been updated to visit ElementNamedWindow.
     */
    public void visit(ElementVisitor v) { }
}