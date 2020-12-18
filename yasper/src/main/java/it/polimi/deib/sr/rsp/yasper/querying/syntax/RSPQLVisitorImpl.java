package it.polimi.deib.sr.rsp.yasper.querying.syntax;

import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.operators.s2r.syntax.WindowNode;
import it.polimi.deib.sr.rsp.api.querying.syntax.RSPQLBaseVisitor;
import it.polimi.deib.sr.rsp.api.querying.syntax.RSPQLParser;
import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.yasper.querying.operators.windowing.WindowNodeImpl;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang.NotImplementedException;

import java.time.Duration;

/**
 * This parser class is based on the RSP-QL syntax described using ANTRL4. The parse tree visitor maps the static
 * syntax parts of the syntax to an extended Jena query. The visitor is based on the SPARQLJenaVisitor.
 */

public class RSPQLVisitorImpl extends RSPQLBaseVisitor {
    private ContinuousQuery query;

    public RSPQLVisitorImpl(ContinuousQuery query) {
        this.query = query;
    }

    /**
     * Set output stream type (ISTREAM, DSTREAM or RSTREAM)
     *
     * @param ctx
     * @return
     */
    public Object visitOutputStreamType(RSPQLParser.OutputStreamTypeContext ctx) {
        switch (ctx.getText()) {
            case "ISTREAM":
                query.setIstream();
                break;
            case "RSTREAM":
                query.setRstream();
                break;
            case "DSTREAM":
                query.setDstream();
                break;
        }
        return null;
    }

    /**
     * Set output stream URI
     *
     * @param ctx
     * @return
     */
    public Object visitOutputStream(RSPQLParser.OutputStreamContext ctx) {
        RSPQLParser.SourceSelectorContext sourceSelectorContext = ctx.sourceSelector();
        RSPQLParser.IriContext iri1 = sourceSelectorContext.iri();
        TerminalNode iriref = iri1.IRIREF();

        String text = iriref.getText();
        String iri = trimTags(text);
        query.setOutputStream(iri);
        //todo not supporting prefixes
        return query.getOutputStream();
    }

    /**
     * Visit window definition clauses. For now we support only  logical windows
     *
     * @param ctx
     * @return
     */
    public Object visitNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx) {
        String windowUri = trimTags(ctx.windowUri().getText());
        String streamUri = trimTags(ctx.streamUri().getText());
        RSPQLParser.LogicalWindowContext c = ctx.window().logicalWindow();
        Duration range = Duration.parse(c.logicalRange().duration().getText());
        Duration step = null;

        if (c.logicalStep() != null) {
            step = Duration.parse(c.logicalStep().duration().getText());
        }

        WindowNode wn = new WindowNodeImpl(RDFUtils.createIRI(windowUri), range, step, 0);

        query.addNamedWindow(streamUri, wn);

        return null;
    }

    public Object visitWindowGraphPattern(RSPQLParser.WindowGraphPatternContext ctx) {
        //Node n = (Node) ctx.varOrIri().accept(this);
        //ElementGroup elg = (ElementGroup) ctx.groupGraphPattern().accept(this);
        //ElementNamedWindow elementNamedWindow = new ElementNamedWindow(n, elg);
        //query.addElementNamedWindow(elementNamedWindow);
        //return new ElementNamedGraph(n, elg);
        return null;
    }

    /**
     * Visit construct template. Can consists of multiple quad blocks.
     *
     * @param ctx
     * @return
     */
    public Object visitConstructTemplate(RSPQLParser.ConstructTemplateContext ctx) {
        //ArrayList<Quad> quads = new ArrayList<>();
        //if(ctx.quads().quadsNotTriples() != null) {
        //    ctx.quads().quadsNotTriples().forEach(graph -> {
        //        Node n = (Node) graph.varOrIri().accept(this);
        //        ElementTriplesBlock etb = (ElementTriplesBlock) graph.triplesTemplate().accept(this);
        //        etb.patternElts().forEachRemaining(t -> {
        //            Quad q = new Quad(n, t);
        //            quads.add(q);
        //        });
        //    });
        //}

        // Triples must be added as quads
        //if(ctx.quads().triplesTemplate() != null) {
        //    ctx.quads().triplesTemplate().forEach(triplesTemplate -> {
        //        ElementTriplesBlock etb = (ElementTriplesBlock) triplesTemplate.accept(this);
        //        etb.getPattern().forEach(triple -> {
        //            quads.add(new Quad(Quad.defaultGraphNodeGenerated, triple));
        //        });
        //    });
        //}

        //Template t = new Template(new QuadAcc(quads));
        //query.setConstructTemplate(t);
        return null;
    }


    /** Pure SPARQL 1.1 parts start here **/

    /**
     * Set query base URI.
     *
     * @param ctx
     * @return
     */
    public Object visitBaseDecl(RSPQLParser.BaseDeclContext ctx) {
        String baseUri = trimTags(ctx.IRIREF().getText());
        // set it
        return null;
    }

    /**
     * Set query prefix.
     *
     * @param ctx
     * @return
     */
    public Object visitPrefixDecl(RSPQLParser.PrefixDeclContext ctx) {
        String prefix = trimLast(ctx.PNAME_NS().getText());
        String ns = trimTags(ctx.IRIREF().getText());
        // set it
        return null;
    }

    /**
     * Set default graph.
     *
     * @param ctx
     * @return
     */
    public Object visitDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx) {
        String defaultGraphUri = trimTags(ctx.sourceSelector().accept(this).toString());
        // set it
        return null;
    }

    /**
     * Add named graph.
     *
     * @param ctx
     * @return
     */
    public Object visitNamedGraphClause(RSPQLParser.NamedGraphClauseContext ctx) {
        String namedGraphUri = trimTags(ctx.sourceSelector().accept(this).toString());
        // add it
        return null;
    }

    /**
     * Visit construct query. Set the type, construct clause, and build children.
     *
     * @param ctx
     * @return
     */
    public Object visitConstructQuery(RSPQLParser.ConstructQueryContext ctx) {
        query.setConstruct();

        if (ctx.triplesTemplate() != null) {
            Object triplesTemplate = ctx.triplesTemplate().accept(this);
            //ElementTriplesBlock elt = (ElementTriplesBlock) ctx.triplesTemplate().accept(this);
            //ElementGroup elg = new ElementGroup();
            //elg.addElement(elt);
            //query.setQueryPattern(elg);
            //query.setConstructTemplate(new Template(elt.getPattern()));
            //ctx.datasetClause().forEach(x -> { x.accept(this); });
            //ctx.solutionModifier().accept(this);
        } else {
            //visitChildren(ctx);
        }
        return null;
    }

    /**
     * Visit triple block.
     *
     * @param ctx
     * @return
     */
    public Object visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx) {
        //ElementTriplesBlock etb = new ElementTriplesBlock();
        RSPQLParser.TriplesTemplateContext t = ctx;
        while (t != null) {
            //ElementTriplesBlock el = (ElementTriplesBlock) t.triplesSameSubject().accept(this);
            //etb.getPattern().addAll(el.getPattern());
            t = t.triplesTemplate();
        }
        //return etb;
        return null; // not correct
    }

    /**
     * Visit construct triples.
     *
     * @param ctx
     * @return
     */
    public Object visitConstructTriples(RSPQLParser.ConstructTriplesContext ctx) {
        //ElementTriplesBlock el = (ElementTriplesBlock) ctx.triplesSameSubject().accept(this);
        //if(ctx.constructTriples() != null){
        //    ElementTriplesBlock elb = (ElementTriplesBlock) ctx.constructTriples().accept(this);
        //    elb.patternElts().forEachRemaining(el::addTriple);
        //}
        //return el;
        return null;
    }

    public Object visitAskQuery(RSPQLParser.AskQueryContext ctx) {
        throw new NotImplementedException();
    }

    public Object visitDescribeQuery(RSPQLParser.DescribeQueryContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit select query.
     *
     * @param ctx
     * @return
     */
    public Object visitSelectClause(RSPQLParser.SelectClauseContext ctx) {
        query.setSelect();
        //query.setQuerySelectType();
        //query.setDistinct(ctx.distinct() != null);
        //query.setReduced(ctx.reduced() != null);
        //List<RSPQLParser.ResultVarContext> resultVars = ctx.resultVar();
        //for(RSPQLParser.ResultVarContext resultVar : resultVars){
        //    String var = trimFirst(resultVar.var().getText());
        //    if(resultVar.expression() != null){
        //        Expr expr = (Expr) resultVar.expression().accept(this);
        //        query.addResultVar(var, expr);
        //    } else {
        //        query.addResultVar(var);
        //    }
        //}
        //return visitChildren(ctx);
        return null;
    }

    /**
     * Visit select query with star pattern.
     *
     * @param ctx
     * @return
     */
    public Object visitResultStar(RSPQLParser.ResultStarContext ctx) {
        // set query result star
        return null;
    }

    /**
     * Visit where clause.
     *
     * @param ctx
     * @return
     */
    public Object visitWhereClause(RSPQLParser.WhereClauseContext ctx) {
        //Element el = (Element) ctx.groupGraphPattern().accept(this);
        //query.setQueryPattern(el);
        return null;
    }

    /**
     * Visit group condition
     *
     * @param ctx
     * @return
     */
    public Object visitGroupCondition(RSPQLParser.GroupConditionContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit order condition.
     *
     * @param ctx
     * @return
     */
    public Object visitOrderClause(RSPQLParser.OrderClauseContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit group graph pattern.
     *
     * @param ctx
     * @return
     */
    public Object visitGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx) {
        //if(ctx.subSelect() != null){
        //    return (Element) ctx.subSelect().accept(this);
        //} else {
        //    return (Element) ctx.groupGraphPatternSub().accept(this);
        //}
        return null;
    }

    /**
     * Visit group graph pattern sub.
     *
     * @param ctx
     * @return
     */
    public Object visitGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx) {
        //ElementGroup elg = new ElementGroup();
        //if(ctx.children == null) {
        //    return elg;
        //}
        //ctx.children.forEach( x -> {
        //    Element el = (Element) x.accept(this);
        //    if(el != null){
        //        elg.addElement(el);
        //    }
        //});
        //return elg;
        return null;
    }

    /**
     * Visit optional pattern.
     *
     * @param ctx
     * @return
     */
    public Object visitOptionalGraphPattern(RSPQLParser.OptionalGraphPatternContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit minus pattern.
     *
     * @param ctx
     * @return
     */
    public Object visitMinusGraphPattern(RSPQLParser.MinusGraphPatternContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit not exists.
     *
     * @param ctx
     * @return
     */
    public Object visitNotExistsFunc(RSPQLParser.NotExistsFuncContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit exists.
     *
     * @param ctx
     * @return
     */
    public Object visitExistsFunc(RSPQLParser.ExistsFuncContext ctx) {
        throw new NotImplementedException();
    }

    public Object visitGroupOrUnionGraphPattern(RSPQLParser.GroupOrUnionGraphPatternContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit triples block.
     *
     * @param ctx
     * @return
     */
    public Object visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx) {
        //ElementPathBlock el = (ElementPathBlock) ctx.triplesSameSubjectPath().accept(this);
        //if(ctx.triplesBlock() != null){
        //    ElementPathBlock elb = (ElementPathBlock) ctx.triplesBlock().accept(this);
        //    elb.patternElts().forEachRemaining(el::addTriplePath);
        //}
        //return el;
        return null;
    }

    /**
     * Visit graph pattern.
     *
     * @param ctx
     * @return
     */
    public Object visitGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx) {
        //Node n = (Node) ctx.varOrIri().accept(this);
        //ElementGroup elg = (ElementGroup) ctx.groupGraphPattern().accept(this);
        //ElementNamedGraph elementNamedGraph = new ElementNamedGraph(n, elg);
        //return elementNamedGraph;
        return null;
    }

    /**
     * Visit triples same subject path.
     *
     * @param ctx
     * @return
     */
    public Object visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit triples same subject.
     *
     * @param ctx
     * @return
     */
    public Object visitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit type. What does this do?
     *
     * @param ctx
     * @return
     */
    public Object visitType(RSPQLParser.TypeContext ctx) {
        //return NodeConst.nodeRDFType;
        return null;
    }

    /**
     * Visit having condition.
     *
     * @param ctx
     * @return
     */
    public Object visitHavingCondition(RSPQLParser.HavingConditionContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit values clause.
     *
     * @param ctx
     * @return
     */
    public Object visitValuesClause(RSPQLParser.ValuesClauseContext ctx) {
        return null;
    }

    /**
     * Visit inline data one var.
     *
     * @param ctx
     * @return
     */
    public Object visitInlineDataOneVar(RSPQLParser.InlineDataOneVarContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit inline data full
     *
     * @param ctx
     * @return
     */
    public Object visitInlineDataFull(RSPQLParser.InlineDataFullContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit undef.
     *
     * @param ctx
     * @return
     */
    public Object visitUndef(RSPQLParser.UndefContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit blank node property list path.
     *
     * @param ctx
     * @return
     */
    public Object visitBlankNodePropertyListPath(RSPQLParser.BlankNodePropertyListPathContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit collection.
     *
     * @param ctx
     * @return
     */
    public Object visitCollection(RSPQLParser.CollectionContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit collection path.
     *
     * @param ctx
     * @return
     */
    public Object visitCollectionPath(RSPQLParser.CollectionPathContext ctx) {
        throw new NotImplementedException();
    }

    /**
     * Visit IRI
     *
     * @param ctx
     * @return
     */
    public Object visitIri(RSPQLParser.IriContext ctx) {
        if (ctx.IRIREF() != null) {
            String uri = trimTags(ctx.IRIREF().getText());
            // create node
            return null;
        }
        return ctx.prefixedName().accept(this);
    }

    /**
     * Visit prefixed name.
     *
     * @param ctx
     * @return
     */
    public Object visitPrefixedName(RSPQLParser.PrefixedNameContext ctx) {
        String value = ctx.getText();
        //value = rootQuery.getPrefixMapping().expandPrefix(value);
        //return NodeFactory.createURI(value);
        // expand the prefixed context
        // return the node
        return null;
    }

    /**
     * Visit variable.
     *
     * @param ctx
     * @return
     */
    public Object visitVar(RSPQLParser.VarContext ctx) {
        String varName = ctx.getText();
        // create var
        return null;
    }

    public Object visitPath(RSPQLParser.PathContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit blank node.
     *
     * @param ctx
     * @return
     */
    public Object visitBlankNode(RSPQLParser.BlankNodeContext ctx) {
        //if(ctx.ANON() != null)
        //    return NodeFactory.createBlankNode();
        //return NodeFactory.createBlankNode(ctx.BLANK_NODE_LABEL().getText());
        return null;
    }

    /**
     * Visit numeric literal negative.
     *
     * @param ctx
     * @return
     */
    public Object visitNumericLiteralNegative(RSPQLParser.NumericLiteralNegativeContext ctx) {
        //if (ctx.INTEGER_NEGATIVE() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        //if (ctx.DECIMAL_NEGATIVE() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        //return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
        return null;
    }

    /**
     * Visit numeric literal positive.
     *
     * @param ctx
     * @return
     */
    public Object visitNumericLiteralPositive(RSPQLParser.NumericLiteralPositiveContext ctx) {
        //if(ctx.INTEGER_POSITIVE() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        //if(ctx.DECIMAL_POSITIVE() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        //return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
        return null;
    }

    /**
     * Visit numeric literal unsigned.
     *
     * @param ctx
     * @return
     */
    public Object visitNumericLiteralUnsigned(RSPQLParser.NumericLiteralUnsignedContext ctx) {
        //if(ctx.INTEGER() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        //if(ctx.DECIMAL() != null)
        //    return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        //return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
        return null;
    }

    /**
     * Visit string.
     *
     * @param ctx
     * @return
     */
    public Object visitString(RSPQLParser.StringContext ctx) {
        //return NodeFactory.createLiteral(trimQuotes(ctx.getText()));
        return null;
    }

    /**
     * Visit boolean literal.
     *
     * @param ctx
     * @return
     */
    public Object visitBooleanLiteral(RSPQLParser.BooleanLiteralContext ctx) {
        //return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDboolean);
        return null;
    }

    /**
     * Visit RDFUtils literal.
     *
     * @param ctx
     * @return
     */
    public Object visitRdfliteral(RSPQLParser.RdfliteralContext ctx) {
        //String lex = trimQuotes(ctx.string().getText());
        //if(ctx.LANGTAG() != null){
        //    String lang = trimFirst(ctx.LANGTAG().toString());
        //    return NodeFactory.createLiteral(lex, lang);
        //} else if(ctx.iri() != null){
        //    String typeUri = ctx.iri().accept(this).toString();
        //    RDFDatatype type = TypeMapper.getInstance().getSafeTypeByName(typeUri);
        //    return NodeFactory.createLiteral(lex, type);
        //}
        //return NodeFactory.createLiteral(lex);
        return null;
    }

    /**
     * Visit filter expression.
     *
     * @param ctx
     * @return
     */
    public Object visitFilter(RSPQLParser.FilterContext ctx) {
        throw new UnsupportedOperationException();
    }

    public Object visitBrackettedExpression(RSPQLParser.BrackettedExpressionContext ctx) {
        return ctx.expression().accept(this);
    }

    /**
     * Visit conditional or expression.
     *
     * @param ctx
     * @return
     */
    public Object visitConditionalOrExpression(RSPQLParser.ConditionalOrExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit conditional expression.
     *
     * @param ctx
     * @return
     */
    public Object visitConditionalAndExpression(RSPQLParser.ConditionalAndExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit relational expression.
     *
     * @param ctx
     * @return
     */
    public Object visitRelationalExpression(RSPQLParser.RelationalExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit additive expression.
     *
     * @param ctx
     * @return
     */
    public Object visitAdditiveExpression(RSPQLParser.AdditiveExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit multiplicative expression.
     *
     * @param ctx
     * @return
     */
    public Object visitMultiplicativeExpression(RSPQLParser.MultiplicativeExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit unary expression.
     *
     * @param ctx
     * @return
     */
    public Object visitUnaryExpression(RSPQLParser.UnaryExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit primary expression.
     *
     * @param ctx
     * @return
     */
    public Object visitPrimaryExpression(RSPQLParser.PrimaryExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit arg list.
     *
     * @param ctx
     * @return
     */
    public Object visitArgList(RSPQLParser.ArgListContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit expression list.
     *
     * @param ctx
     * @return
     */
    public Object visitExpressionList(RSPQLParser.ExpressionListContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit regex expression.
     *
     * @param ctx
     * @return
     */
    public Object visitRegexExpression(RSPQLParser.RegexExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit bind.
     *
     * @param ctx
     * @return
     */
    public Object visitBind(RSPQLParser.BindContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit subselect.
     *
     * @param ctx
     * @return
     */
    public Object visitSubSelect(RSPQLParser.SubSelectContext ctx) {
        //Query head = query;
        //query = new RSPQLJenaQuery(); // Use regular Jena query if stream queries are not supported in sub-queries
        //visitChildren(ctx);
        //Element el = new ElementSubQuery(query);
        //query = head;
        //return el;
        return null;
    }

    /**
     * Set limit.
     *
     * @param ctx
     * @return
     */
    public Object visitLimitClause(RSPQLParser.LimitClauseContext ctx) {
        int i = Integer.parseInt(ctx.INTEGER().getText());
        // set it
        return null;
    }

    /**
     * Set offset.
     *
     * @param ctx
     * @return
     */
    public Object visitOffsetClause(RSPQLParser.OffsetClauseContext ctx) {
        int i = Integer.parseInt(ctx.INTEGER().getText());
        // set it
        return null;
    }


    /**
     * Visit aggregate.
     *
     * @param ctx
     * @return
     */
    public Object visitAggregate(RSPQLParser.AggregateContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit substring expression.
     *
     * @param ctx
     * @return
     */
    public Object visitSubstringExpression(RSPQLParser.SubstringExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit string replace function.
     *
     * @param ctx
     * @return
     */
    public Object visitStrReplaceExpression(RSPQLParser.StrReplaceExpressionContext ctx) {
        throw new UnsupportedOperationException();
    }

    /**
     * Visit built in call.
     *
     * @param ctx
     * @return
     */
    public Object visitBuiltInCall(RSPQLParser.BuiltInCallContext ctx) {
        throw new UnsupportedOperationException();
    }

    public String trimTags(String s) {
        return s.replaceAll("^<(.*)>$", "$1");
    }

    public String trimQuotes(String s) {
        return s.replaceAll("^['\"](.*)['\"]$", "$1");
    }

    public String trimFirst(String s) {
        return s.replaceAll("^.(.*)$", "$1");
    }

    public String trimLast(String s) {
        return s.replaceAll("^(.*).$", "$1");
    }

}

