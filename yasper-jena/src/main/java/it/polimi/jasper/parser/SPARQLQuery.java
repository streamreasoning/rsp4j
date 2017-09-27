package it.polimi.jasper.parser;

import it.polimi.jasper.parser.sparql.Prefix;
import lombok.Data;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.Node_URI;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.riot.system.IRIResolver;
import org.apache.jena.sparql.core.Prologue;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.expr.Expr;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.syntax.ElementGroup;
import org.apache.jena.sparql.syntax.Template;
import org.apache.jena.sparql.syntax.TripleCollectorMark;

import static org.apache.jena.query.Query.*;

/**
 * Created by Riccardo on 05/08/16.
 */
@Data
public class SPARQLQuery {

    protected Query query;


    public SPARQLQuery(Prologue p) {
        this.query = new Query();
        this.query.usePrologueFrom(p);
    }

    public SPARQLQuery() {
        this.query = new Query();
    }

    public SPARQLQuery setSelectQuery() {
        query.setQuerySelectType();
        return this;
    }

    public Query getQ() {
        return query;
    }

    public SPARQLQuery setConstructQuery() {
        query.setQueryConstructType();
        return this;
    }

    public SPARQLQuery setDescribeQuery() {
        query.setQueryDescribeType();
        return this;
    }

    public SPARQLQuery setAskQuery() {
        query.setQueryAskType();
        return this;
    }

    public SPARQLQuery setDistinct() {
        query.setDistinct(true);
        return this;
    }

    public SPARQLQuery setQueryStar() {
        query.setQueryResultStar(true);
        return this;
    }

    public SPARQLQuery addNamedGraphURI(Node_URI match) {
        query.addNamedGraphURI(match.getURI());
        return this;
    }

    public SPARQLQuery addGraphURI(Node_URI match) {
        query.addGraphURI(match.getURI());
        return this;
    }

    public SPARQLQuery addElement(ElementGroup sub) {
        query.setQueryPattern(sub);
        return this;
    }

    public TripleCollectorMark insert(TripleCollectorMark acc, Triple t) {
        acc.addTriple(acc.mark(), t);
        return acc;
    }

    public SPARQLQuery setQBaseURI(String match) {
        query.setBaseURI(match);
        return this;
    }

    public SPARQLQuery setPrefix(Prefix pop) {
        query.setPrefix(pop.getPrefix(), pop.getUri());
        return this;
    }

    public Expr allocQAggregate(Aggregator custom) {
        return query.allocAggregate(custom);

    }

    public SPARQLQuery addOrderBy(Object n) {
        return (n instanceof Node) ? addOrderBy((Node) n) : addOrderBy((Expr) n);
    }

    public SPARQLQuery addOrderBy(Node n) {
        query.addOrderBy(n, ORDER_DEFAULT);
        return this;
    }

    public SPARQLQuery addOrderBy(Expr n) {
        query.addOrderBy(n, ORDER_DEFAULT);
        return this;
    }

    public SPARQLQuery addOrderBy(Expr pop, String s) {
        query.addOrderBy(pop, "DESC".equals(s) ? ORDER_DESCENDING : ORDER_ASCENDING);
        return this;
    }

    public SPARQLQuery setLimit(String limit) {
        query.setLimit(Integer.parseInt(limit.trim()));
        return this;
    }

    public SPARQLQuery setOffset(String offset) {
        query.setOffset(Integer.parseInt(offset.trim()));
        return this;
    }

    public SPARQLQuery addQGroupBy(Expr pop) {
        query.addGroupBy(null, pop);
        return this;
    }

    public SPARQLQuery addQGroupBy(Var v, Expr pop) {
        query.addGroupBy(v, pop);
        return this;
    }

    public SPARQLQuery addQGroupBy(Var v) {
        query.addGroupBy(v);
        return this;
    }

    public SPARQLQuery setReduced() {
        query.setReduced(true);
        return this;
    }

    public SPARQLQuery addQCResultVar(Node pop, Expr pop1) {
        addQCResultVar(pop, pop1);
        query.setQueryResultStar(false);
        return this;
    }

    public SPARQLQuery addQCResultVar(Node pop) {
        query.addResultVar(pop);
        query.setQueryResultStar(false);
        return this;
    }

    public SPARQLQuery addQHavingCondition(Expr pop) {
        query.addHavingCondition(pop);
        return this;
    }

    public SPARQLQuery setQConstructTemplate(Template template) {
        query.setConstructTemplate(template);
        return this;
    }

    public SPARQLQuery addQDescribeNode(Node pop) {
        query.addDescribeNode(pop);
        query.setQueryResultStar(false);
        return this;
    }

    public String resolveSilent(String iriStr) {
        return query.getResolver().resolveSilent(iriStr).toString();
    }

    public SPARQLQuery setQResolver(IRIResolver resolver) {
        query.setResolver(resolver);
        return this;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}