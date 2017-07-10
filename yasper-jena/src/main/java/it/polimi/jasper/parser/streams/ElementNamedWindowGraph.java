package it.polimi.jasper.parser.streams;

import lombok.Getter;
import org.apache.jena.graph.Node;
import org.apache.jena.sparql.syntax.Element;
import org.apache.jena.sparql.syntax.ElementNamedGraph;
import org.apache.jena.sparql.syntax.ElementVisitor;

/**
 * Created by Riccardo on 12/08/16.
 */
@Getter
public class ElementNamedWindowGraph extends ElementNamedGraph {

    public ElementNamedWindowGraph(Node source_name, Element el) {
        super(source_name, el);
    }

    public void visit(ElementVisitor v) {
        super.visit(v);
    }
}
