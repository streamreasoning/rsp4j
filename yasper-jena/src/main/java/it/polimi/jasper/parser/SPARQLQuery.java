package it.polimi.jasper.parser;

import it.polimi.jasper.parser.sparql.Prefix;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Node_URI;
import org.apache.jena.graph.Triple;
import org.apache.jena.iri.IRI;
import org.apache.jena.query.Query;
import org.apache.jena.riot.checker.CheckerIRI;
import org.apache.jena.riot.system.ErrorHandlerFactory;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.Template;
import org.apache.jena.sparql.syntax.TripleCollectorMark;

/**
 * Created by Riccardo on 05/08/16.
 */
@Data
@NoArgsConstructor
public class SPARQLQuery extends Query {

    public SPARQLQuery(Prologue prologue) {
        super(prologue);
    }

    public SPARQLQuery setSelectQuery() {
        setQuerySelectType();
        return this;
    }

    public Query getQ() {
        return this;
    }

    public SPARQLQuery setConstructQuery() {
        setQueryConstructType();
        return this;
    }

    public SPARQLQuery setDescribeQuery() {
        setQueryDescribeType();
        return this;
    }

    public SPARQLQuery setAskQuery() {
        setQueryAskType();
        return this;
    }

    public SPARQLQuery setDistinct() {
        setDistinct(true);
        return this;
    }

    public SPARQLQuery setQueryStar() {
        setQueryResultStar(true);
        return this;
    }

    public SPARQLQuery addNamedGraphURI(Node_URI match) {
        addNamedGraphURI(match.getURI());
        return this;
    }

    public SPARQLQuery addGraphURI(Node_URI match) {
        addGraphURI(match.getURI());
        return this;
    }

    public SPARQLQuery addElement(ElementGroup sub) {
        setQueryPattern(sub);
        return this;
    }

    public TripleCollectorMark insert(TripleCollectorMark acc, Triple t) {
        acc.addTriple(acc.mark(), t);
        return acc;
    }

    public SPARQLQuery setQBaseURI(String match) {
        setBaseURI(match);
        return this;
    }

    public SPARQLQuery setPrefix(Prefix pop) {
        setPrefix(pop.getPrefix(), pop.getUri());
        return this;
    }

    public Expr allocQAggregate(Aggregator custom) {
        return allocAggregate(custom);

    }

    public SPARQLQuery addOrderBy(Object n) {
        return (n instanceof Node) ? addOrderBy((Node) n) : addOrderBy((Expr) n);
    }

    public SPARQLQuery addOrderBy(Node n) {
        addOrderBy(n, ORDER_DEFAULT);
        return this;
    }

    public SPARQLQuery addOrderBy(Expr n) {
        addOrderBy(n, ORDER_DEFAULT);
        return this;
    }

    public SPARQLQuery addOrderBy(Expr pop, String s) {
        addOrderBy(pop, "DESC".equals(s) ? ORDER_DESCENDING : ORDER_ASCENDING);
        return this;
    }

    public SPARQLQuery setLimit(String limit) {
        setLimit(Integer.parseInt(limit.trim()));
        return this;
    }

    public SPARQLQuery setOffset(String offset) {
        setOffset(Integer.parseInt(offset.trim()));
        return this;
    }

    public SPARQLQuery addQGroupBy(Expr pop) {
        addGroupBy((Var) null, pop);
        return this;
    }

    public SPARQLQuery addQGroupBy(Var v, Expr pop) {
        addGroupBy(v, pop);
        return this;
    }

    public SPARQLQuery addQGroupBy(Var v) {
        addGroupBy(v);
        return this;
    }

    public SPARQLQuery setReduced() {
        setReduced(true);
        return this;
    }

    public SPARQLQuery addQCResultVar(Node pop, Expr pop1) {
        addQCResultVar(pop, pop1);
        setQueryResultStar(false);
        return this;
    }

    public SPARQLQuery addQCResultVar(Node pop) {
        addResultVar(pop);
        setQueryResultStar(false);
        return this;
    }

    public SPARQLQuery addQHavingCondition(Expr pop) {
        addHavingCondition(pop);
        return this;
    }

    public SPARQLQuery setQConstructTemplate(Template template) {
        setConstructTemplate(template);
        return this;
    }

    public SPARQLQuery addQDescribeNode(Node pop) {
        addDescribeNode(pop);
        setQueryResultStar(false);
        return this;
    }

    public String resolveSilent(String iriStr) {
        if (resolver == null) {
            resolver = IRIResolver.create();
        }
        IRI iri = resolver.resolveSilent(iriStr);
        CheckerIRI.iriViolations(iri, ErrorHandlerFactory.getDefaultErrorHandler());
        return iri.toString();
    }

    public SPARQLQuery setQResolver(IRIResolver resolver) {
        setResolver(resolver);
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}