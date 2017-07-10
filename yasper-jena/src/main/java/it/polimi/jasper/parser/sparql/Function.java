package it.polimi.jasper.parser.sparql;

import org.apache.jena.graph.Node_URI;
import org.apache.jena.sparql.expr.E_Function;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.expr.aggregate.AggregatorFactory;
import org.apache.jena.sparql.expr.aggregate.Args;

/**
 * Created by Riccardo on 06/08/16.
 */
public class Function {

    private final String iri;
    private Args args;

    public Function(Args pop, Node_URI match) {
        this.iri = match.getURI();
        this.args = pop;
    }

    public String getIri() {
        return iri;
    }

    public Aggregator createCustom() {
        return AggregatorFactory.createCustom(iri, args);

    }

    public Expr build() {
        return new E_Function(iri, args);
    }
}
