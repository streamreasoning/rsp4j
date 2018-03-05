package rspql.syntax;


import it.polimi.yasper.core.rspql.syntax.RSPQLBaseVisitor;
import it.polimi.yasper.core.rspql.syntax.RSPQLParser;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.datatypes.TypeMapper;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.sparql.core.BasicPattern;
import org.apache.jena.sparql.core.TriplePath;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.binding.BindingFactory;
import org.apache.jena.sparql.expr.*;
import org.apache.jena.sparql.expr.aggregate.Aggregator;
import org.apache.jena.sparql.expr.aggregate.AggregatorFactory;
import org.apache.jena.sparql.expr.aggregate.Args;
import org.apache.jena.sparql.graph.NodeConst;
import org.apache.jena.sparql.path.Path;
import org.apache.jena.sparql.path.PathParser;
import org.apache.jena.sparql.syntax.*;
import org.apache.jena.sparql.util.ExprUtils;
import org.apache.jena.vocabulary.RDF;

import java.util.List;

/**
 * This parser class is based on the RSP-QL syntax described using ANTRL4. The parse tree visitor maps the static
 * syntax parts of the syntax to a Jena query.
 *
 * The SPARQLJenaVisitor currently has the following known limitations:
 * - No support for complex values in collections e.g. SELECT * WHERE { ?s ?p ( [ a <test> ] ) }
 */

public class SPARQL11JenaVisitor extends RSPQLBaseVisitor {
    private Query rootQuery;
    private Query query;

    public SPARQL11JenaVisitor(Query query){
        rootQuery = query;
        this.query = query;
    }

    /** SPARQL 1.1 parser starts here **/

    public Object visitBaseDecl(RSPQLParser.BaseDeclContext ctx) {
        rootQuery.setBaseURI(trimTags(ctx.IRIREF().getText()));
        return null;
    }

    public Object visitPrefixDecl(RSPQLParser.PrefixDeclContext ctx) {
        String prefix = trimLast(ctx.PNAME_NS().getText());
        String ns = trimTags(ctx.IRIREF().getText());
        rootQuery.setPrefix(prefix, ns);
        return null;
    }

    public Object visitDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx) {
        String uri = trimTags(ctx.sourceSelector().accept(this).toString());
        query.addGraphURI(uri);
        return null;
    }

    public Object visitNamedGraphClause(RSPQLParser.NamedGraphClauseContext ctx) {
        String uri = trimTags(ctx.sourceSelector().accept(this).toString());
        query.addNamedGraphURI(uri);
        return null;
    }

    public Object visitConstructQuery(RSPQLParser.ConstructQueryContext ctx) {
        query.setQueryConstructType();
        if(ctx.triplesTemplate() != null){
            ElementTriplesBlock elt = (ElementTriplesBlock) ctx.triplesTemplate().accept(this);
            ElementGroup elg = new ElementGroup();
            elg.addElement(elt);
            query.setQueryPattern(elg);
            query.setConstructTemplate(new Template(elt.getPattern()));
            ctx.datasetClause().forEach(x -> { x.accept(this); });
            ctx.solutionModifier().accept(this);
        } else {
            visitChildren(ctx);
        }
        return null;
    }

    public Element visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx) {
        ElementTriplesBlock etb = new ElementTriplesBlock();
        RSPQLParser.TriplesTemplateContext t = ctx;
        while(t != null){
            ElementTriplesBlock el = (ElementTriplesBlock) t.triplesSameSubject().accept(this);
            etb.getPattern().addAll(el.getPattern());
            t = t.triplesTemplate();
        }
        return etb;
    }

    public Object visitConstructTemplate(RSPQLParser.ConstructTemplateContext ctx) {
        BasicPattern bgp = new BasicPattern();
        if(ctx.quads().triplesTemplate() != null) {
            ctx.quads().triplesTemplate().forEach(triplesTemplate -> {
                ElementTriplesBlock etb = (ElementTriplesBlock) triplesTemplate.accept(this);
                etb.patternElts().forEachRemaining(bgp::add);
            });
        }
        query.setConstructTemplate(new Template(bgp));
        return null;
    }

    public Object visitConstructTriples(RSPQLParser.ConstructTriplesContext ctx) {
        ElementTriplesBlock el = (ElementTriplesBlock) ctx.triplesSameSubject().accept(this);
        if(ctx.constructTriples() != null){
            ElementTriplesBlock elb = (ElementTriplesBlock) ctx.constructTriples().accept(this);
            elb.patternElts().forEachRemaining(el::addTriple);
        }
        return el;
    }

    public Object visitAskQuery(RSPQLParser.AskQueryContext ctx) {
        query.setQueryAskType();
        return visitChildren(ctx);
    }

    public Object visitDescribeQuery(RSPQLParser.DescribeQueryContext ctx) {
        query.setQueryDescribeType();
        ctx.varOrIri().forEach((varOrIri) -> {
            query.addDescribeNode((Node) varOrIri.accept(this));
        });
        return visitChildren(ctx);
    }

    public Object visitSelectClause(RSPQLParser.SelectClauseContext ctx) {
        query.setQuerySelectType();
        query.setDistinct(ctx.distinct() != null);
        query.setReduced(ctx.reduced() != null);
        List<RSPQLParser.ResultVarContext> resultVars = ctx.resultVar();
        for(RSPQLParser.ResultVarContext resultVar : resultVars){
            String var = trimFirst(resultVar.var().getText());
            if(resultVar.expression() != null){
                Expr expr = (Expr) resultVar.expression().accept(this);
                query.addResultVar(var, expr);
            } else {
                query.addResultVar(var);
            }
        }
        return visitChildren(ctx);
    }

    public Object visitResultStar(RSPQLParser.ResultStarContext ctx) {
        query.setQueryResultStar(true);
        return null;
    }

    public Object visitWhereClause(RSPQLParser.WhereClauseContext ctx) {
        Element el = (Element) ctx.groupGraphPattern().accept(this);
        query.setQueryPattern(el);
        return null;
    }

    public Object visitGroupCondition(RSPQLParser.GroupConditionContext ctx) {
        if (ctx.builtInCall() != null) {
            query.addGroupBy((Expr) ctx.builtInCall().accept(this));
        } else if (ctx.functionCall() != null) {
            query.addGroupBy((Expr) ctx.functionCall().accept(this));
        } else if (ctx.expression() != null) {
            Expr expr = (Expr) ctx.expression().accept(this);
            if (ctx.var() != null) {
                Var var = (Var) ctx.var().accept(this);
                query.addGroupBy(var, expr);
            } else {
                query.addGroupBy(expr);
            }
        } else if (ctx.var() != null) {
            query.addGroupBy((Var) ctx.var().accept(this));
        }
        return null;
    }

    public Object visitOrderClause(RSPQLParser.OrderClauseContext ctx) {
        for (RSPQLParser.OrderConditionContext c : ctx.orderCondition()) {
            int direction = Query.ORDER_DEFAULT;
            Expr expr;
            if (c.brackettedExpression() != null) {
                if (c.getChild(0).getText().equals("ASC")) {
                    direction = query.ORDER_ASCENDING;
                } else if (c.getChild(0).getText().equals("DESC")) {
                    direction = query.ORDER_DESCENDING;
                }
                expr = (Expr) c.brackettedExpression().accept(this);
                query.addOrderBy(expr, direction);
            } else if (c.var() != null) {
                Var v = (Var) c.var().accept(this);
                query.addOrderBy(v, direction);
            } else {
                expr = (Expr) c.constraint().accept(this);
                query.addOrderBy(expr, direction);
            }
        }
        return null;
    }

    /**
     * This function could possibly be removed.
     * @param ctx
     * @return
     */
    public Element visitGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx) {
        if(ctx.subSelect() != null){
            return (Element) ctx.subSelect().accept(this);
        } else {
            return (Element) ctx.groupGraphPatternSub().accept(this);
        }
    }

    public ElementGroup visitGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx) {
        ElementGroup elg = new ElementGroup();
        if(ctx.children == null) {
            return elg;
        }
        ctx.children.forEach( x -> {
            Element el = (Element) x.accept(this);
            if(el != null){
                elg.addElement(el);
            }
        });
        return elg;
    }

    public Element visitOptionalGraphPattern(RSPQLParser.OptionalGraphPatternContext ctx) {
        Element el = (Element) ctx.groupGraphPattern().accept(this);
        return new ElementOptional(el);
    }

    public Element visitMinusGraphPattern(RSPQLParser.MinusGraphPatternContext ctx) {
        Element el = (Element) ctx.groupGraphPattern().accept(this);
        return new ElementMinus(el);
    }

    public Expr visitNotExistsFunc(RSPQLParser.NotExistsFuncContext ctx) {
        Element el = (Element) ctx.groupGraphPattern().accept(this);
        return new E_NotExists(el);
    }

    public Expr visitExistsFunc(RSPQLParser.ExistsFuncContext ctx) {
        Element el = (Element) ctx.groupGraphPattern().accept(this);
        return new E_Exists(el);
    }

    public Element visitGroupOrUnionGraphPattern(RSPQLParser.GroupOrUnionGraphPatternContext ctx) {
        if(ctx.groupGraphPattern().size() > 1) {
            ElementUnion el = new ElementUnion();
            for (RSPQLParser.GroupGraphPatternContext i : ctx.groupGraphPattern()) {
                el.addElement((Element) i.accept(this));
            }
            return el;
        }
        return (Element) ctx.groupGraphPattern().get(0).accept(this);
    }

    public ElementPathBlock visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx) {
        ElementPathBlock el = (ElementPathBlock) ctx.triplesSameSubjectPath().accept(this);
        if(ctx.triplesBlock() != null){
            ElementPathBlock elb = (ElementPathBlock) ctx.triplesBlock().accept(this);
            elb.patternElts().forEachRemaining(el::addTriplePath);
        }
        return el;
    }

    public Object visitGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx) {
        Node n = (Node) ctx.varOrIri().accept(this);
        ElementGroup elg = (ElementGroup) ctx.groupGraphPattern().accept(this);
        ElementNamedGraph elementNamedGraph = new ElementNamedGraph(n, elg);
        return elementNamedGraph;
    }

    public ElementPathBlock visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx) {
        ElementPathBlock elb = new ElementPathBlock();
        ElementPathBlock bnodeBlocks = new ElementPathBlock();

        // varOrTerm  propertyListPathNotEmpty | triplesNodePath  propertyListPathNotEmpty?
        Node subject;
        if (ctx.triplesNodePath() != null) {
            ElementPathBlock el = (ElementPathBlock) ctx.triplesNodePath().accept(this);
            bnodeBlocks.getPattern().addAll(el.getPattern());
            subject = el.getPattern().iterator().next().getSubject();

        } else {
            subject = (Node) ctx.varOrTerm().accept(this);
        }

        if (ctx.propertyListPathNotEmpty() != null){
            for(RSPQLParser.PropertyListPathContext p : ctx.propertyListPathNotEmpty().propertyListPath()){
                Node property = null;
                Path propertyPath = null;

                // Property
                if(p.verbSimple() != null) {
                    property = (Node) p.verbSimple().accept(this);
                } else if(p.verbPath() != null) {
                    propertyPath = (Path) p.verbPath().accept(this);
                }

                // Objects
                for(RSPQLParser.ObjectPathContext o : p.objectListPath().objectPath()){
                    Object object = o.accept(this);
                    if(object instanceof ElementPathBlock) {
                        ElementPathBlock el = (ElementPathBlock) object;
                        bnodeBlocks.getPattern().addAll(el.getPattern());
                        object = el.getPattern().iterator().next().getSubject();
                    }

                    if(property != null) {
                        Triple triple = new Triple(subject, property, (Node) object);
                        elb.addTriple(triple);
                    }
                    if(propertyPath != null) {
                        TriplePath triplePath = new TriplePath(subject, propertyPath, (Node) object);
                        elb.addTriplePath(triplePath);
                    }
                }
            }
        }
        elb.getPattern().addAll(bnodeBlocks.getPattern());
        return elb;
    }

    public ElementTriplesBlock visitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx) {
        ElementTriplesBlock etb = new ElementTriplesBlock();

        // varOrTerm  propertyListNotEmpty
        Node subject = (Node) ctx.varOrTerm().accept(this);
        for(RSPQLParser.PropertyListContext p : ctx.propertyListNotEmpty().propertyList()){
            // Property
            Node property = (Node) p.verb().accept(this);

            // Objects
            for(RSPQLParser.ObjectContext o : p.objectList().object()){
                Node object = (Node) o.accept(this);
                Triple triple = new Triple(subject, property, object);
                etb.addTriple(triple);
            }
        }
        return etb;
    }

    public Node visitType(RSPQLParser.TypeContext ctx) {
        return NodeConst.nodeRDFType;
    }

    public Object visitHavingCondition(RSPQLParser.HavingConditionContext ctx) {
        query.addHavingCondition((Expr) ctx.constraint().accept(this));
        return null;
    }

    public Object visitValuesClause(RSPQLParser.ValuesClauseContext ctx) {
        if(ctx.dataBlock() != null) {
            ElementData el = (ElementData) ctx.dataBlock().accept(this);
            query.setValuesDataBlock(el.getVars(), el.getRows());
        }
        return null;
    }

    public Element visitInlineDataOneVar(RSPQLParser.InlineDataOneVarContext ctx) {
        ElementData el = new ElementData();

        Var var = (Var) ctx.var().accept(this);
        el.add(var);
        for(RSPQLParser.DataBlockValueContext dataBlock : ctx.dataBlockValue()){
            Node value = (Node) dataBlock.accept(this);
            Binding binding = BindingFactory.binding(var, value);
            el.add(binding);
        }
        return el;
    }

    public Element visitInlineDataFull(RSPQLParser.InlineDataFullContext ctx) {
        ElementData el = new ElementData();

        for(RSPQLParser.VarContext v : ctx.var()){
            el.add((Var) v.accept(this));
        }

        List<Var> vars = el.getVars();
        for(RSPQLParser.DataBlockValuesContext dataBlock : ctx.dataBlockValues()){
            Binding binding = null;
            for(int i = 0; i < vars.size(); i++){
                Var var = vars.get(i % vars.size());
                Node value = (Node) dataBlock.dataBlockValue(i).accept(this);
                binding = BindingFactory.binding(binding, var, value);
            }
            if(binding != null)
                el.add(binding);
        }
        return el;
    }

    public Node visitUndef(RSPQLParser.UndefContext ctx) {
        return null;
    }

    public ElementPathBlock visitBlankNodePropertyListPath(RSPQLParser.BlankNodePropertyListPathContext ctx) {
        ElementPathBlock elb = new ElementPathBlock();
        Node subject = NodeFactory.createBlankNode();

        for(RSPQLParser.PropertyListPathContext p : ctx.propertyListPathNotEmpty().propertyListPath()){
            Node property = null;
            Path propertyPath = null;

            // Property
            if(p.verbSimple() != null) {
                property = (Node) p.verbSimple().accept(this);
            } else if(p.verbPath() != null) {
                propertyPath = (Path) p.verbPath().accept(this);
            }

            // Objects
            for(RSPQLParser.ObjectPathContext o : p.objectListPath().objectPath()){
                Node object = (Node) o.accept(this);
                if(property != null) {
                    Triple triple = new Triple(subject, property, object);
                    elb.addTriple(triple);
                }
                if(propertyPath != null) {
                    TriplePath triplePath = new TriplePath(subject, propertyPath, object);
                    elb.addTriplePath(triplePath);
                }
            }
        }
        return elb;
    }

    public Object visitCollection(RSPQLParser.CollectionContext ctx) {
        System.err.println("visitCollection: Not implemented");
        return null;
    }

    public ElementPathBlock visitCollectionPath(RSPQLParser.CollectionPathContext ctx) {
        ElementPathBlock epb = new ElementPathBlock();
        Node current = NodeFactory.createBlankNode();
        int i = 0;
        while(i < ctx.graphNodePath().size()){
            Object o = ctx.graphNodePath(i).accept(this);
            if(o instanceof ElementPathBlock){
                System.err.println("Complex values not yet supported in collections");
                return epb;
            }
            epb.addTriple(new Triple(current, RDF.first.asNode(), (Node) o));
            i++;
            if(ctx.graphNodePath().size() == i){
                break;
            }
            Node rest = NodeFactory.createBlankNode();
            epb.addTriple(new Triple(current, RDF.rest.asNode(), rest));
            current = rest;
        }
        epb.addTriple(new Triple(current, RDF.rest.asNode(), RDF.nil.asNode()));
        return epb;
    }

    public Node visitIri(RSPQLParser.IriContext ctx) {
        if(ctx.IRIREF() != null){
            String uri =  trimTags(ctx.IRIREF().getText());
            return  NodeFactory.createURI(uri);
        }
        return (Node) ctx.prefixedName().accept(this);
    }

    public Node visitPrefixedName(RSPQLParser.PrefixedNameContext ctx) {
        String value = ctx.getText();
        value = rootQuery.getPrefixMapping().expandPrefix(value);
        return NodeFactory.createURI(value);
    }

    public Node visitVar(RSPQLParser.VarContext ctx) {
        String varName = Var.canonical(ctx.getText());
        Node n = Var.alloc(varName);
        return n;
    }

    public Path visitPath(RSPQLParser.PathContext ctx) {
        Path p = PathParser.parse(ctx.getText(), rootQuery.getPrefixMapping());
        return p;
    }

    public Node visitBlankNode(RSPQLParser.BlankNodeContext ctx){
        if(ctx.ANON() != null)
            return NodeFactory.createBlankNode();
        return NodeFactory.createBlankNode(ctx.BLANK_NODE_LABEL().getText());
    }

    public Node visitNumericLiteralNegative(RSPQLParser.NumericLiteralNegativeContext ctx) {
        if (ctx.INTEGER_NEGATIVE() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        if (ctx.DECIMAL_NEGATIVE() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
    }

    public Node visitNumericLiteralPositive(RSPQLParser.NumericLiteralPositiveContext ctx) {
        if(ctx.INTEGER_POSITIVE() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        if(ctx.DECIMAL_POSITIVE() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
    }

    public Node visitNumericLiteralUnsigned(RSPQLParser.NumericLiteralUnsignedContext ctx) {
        if(ctx.INTEGER() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDinteger);
        if(ctx.DECIMAL() != null)
            return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdecimal);
        return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDdouble);
    }

    public Node visitString(RSPQLParser.StringContext ctx) {
        return NodeFactory.createLiteral(trimQuotes(ctx.getText()));
    }

    public Node visitBooleanLiteral(RSPQLParser.BooleanLiteralContext ctx) {
        return NodeFactory.createLiteral(ctx.getText(), XSDDatatype.XSDboolean);
    }

    public Object visitRdfliteral(RSPQLParser.RdfliteralContext ctx) {
        String lex = trimQuotes(ctx.string().getText());
        if(ctx.LANGTAG() != null){
            String lang = trimFirst(ctx.LANGTAG().toString());
            return NodeFactory.createLiteral(lex, lang);
        } else if(ctx.iri() != null){
            String typeUri = ctx.iri().accept(this).toString();
            RDFDatatype type = TypeMapper.getInstance().getSafeTypeByName(typeUri);
            return NodeFactory.createLiteral(lex, type);
        }
        return NodeFactory.createLiteral(lex);
    }

    public Object visitFilter(RSPQLParser.FilterContext ctx) {
        Expr expr = (Expr) ctx.constraint().accept(this);
        ctx.constraint().accept(this);
        return new ElementFilter(expr);
    }

    public Object visitBrackettedExpression(RSPQLParser.BrackettedExpressionContext ctx) {
        return ctx.expression().accept(this);
    }

    public Expr visitConditionalOrExpression(RSPQLParser.ConditionalOrExpressionContext ctx) {
        Expr expr1 = null;
        for(RSPQLParser.ConditionalAndExpressionContext i : ctx.conditionalAndExpression()){
            Expr expr2 = (Expr) i.accept(this);
            if(expr1 != null) {
                expr2 = new E_LogicalOr(expr1, expr2);
            }
            expr1 = expr2;
        }
        return expr1;
    }

    public Expr visitConditionalAndExpression(RSPQLParser.ConditionalAndExpressionContext ctx) {
        Expr expr1 = null;
        for (RSPQLParser.ValueLogicalContext i : ctx.valueLogical()) {
            Expr expr2 = (Expr) i.accept(this);
            if (expr1 != null) {
                expr2 = new E_LogicalAnd(expr1, expr2);
            }
            expr1 = expr2;
        }
        return expr1;
    }

    public Expr visitRelationalExpression(RSPQLParser.RelationalExpressionContext ctx) {
        Expr expr1 = null;
        for(RSPQLParser.NumericExpressionContext i : ctx.numericExpression()){
            Expr expr2 = (Expr) i.accept(this);
            if(expr1 != null) {
                String op = ctx.getChild(1).getText();
                if (op.equals("=")) {
                    expr2 = new E_Equals(expr1, expr2);
                } else if (op.equals("!=")) {
                    expr2 = new E_NotEquals(expr1, expr2);
                } else if (op.equals("<")) {
                    expr2 = new E_LessThan(expr1, expr2);
                }  else if (op.equals(">")) {
                    expr2 = new E_GreaterThan(expr1, expr2);
                } else if (op.equals("<=")) {
                    expr2 = new E_LessThanOrEqual(expr1, expr2);
                } else if (op.equals(">=")) {
                    expr2 = new E_GreaterThanOrEqual(expr1, expr2);
                }
            }
            expr1 = expr2;
        }

        if(ctx.expressionList() != null){
            ExprList exprList = (ExprList) ctx.expressionList().accept(this);
            String op = ctx.getChild(1).getText();
            if(op.toUpperCase().equals("IN")){
                expr1 = new E_OneOf(expr1, exprList);
            } else {
                expr1 = new E_NotOneOf(expr1, exprList);
            }
        }
        return expr1;
    }

    public Expr visitAdditiveExpression(RSPQLParser.AdditiveExpressionContext ctx) {
        Expr expr1 = (Expr) ctx.multiplicativeExpression().accept(this);

        for(RSPQLParser.MultiExprContext i : ctx.multiExpr()){
            Expr expr2;
            if(i.multiplicativeExpression() != null){
                expr2 = (Expr) i.multiplicativeExpression().accept(this);
                // addObservable expr
                String op = i.getChild(0).getText();
                if(op.equals("+")){
                    expr1 = new E_Add(expr1, expr2);
                } else if(op.equals("-")){
                    expr1 = new E_Subtract(expr1, expr2);
                }
            } else {
                if(i.numericLiteralPositive() != null){
                    String s = trimFirst(i.numericLiteralPositive().getText());
                    Node n = NodeFactory.createLiteral(s, XSDDatatype.XSDinteger);
                    expr2 = ExprUtils.nodeToExpr(n);
                    expr1 = new E_Add(expr1, expr2);
                } else if(i.numericLiteralNegative() != null){
                    String s = trimFirst(i.numericLiteralNegative().getText());
                    Node n = NodeFactory.createLiteral(s, XSDDatatype.XSDinteger);
                    expr2 = ExprUtils.nodeToExpr(n);
                    expr1 = new E_Subtract(expr1, expr2);
                }
                // Unary expr can still follow?
            }
        }
        return expr1;
    }

    public Expr visitMultiplicativeExpression(RSPQLParser.MultiplicativeExpressionContext ctx) {
        Expr expr1 = null;
        for(RSPQLParser.UnaryExpressionContext i : ctx.unaryExpression()){
            Expr expr2 = (Expr) i.accept(this);
            if(expr1 != null) {
                String op = ctx.getChild(1).getText();
                if (op.equals("*")) {
                    expr2 = new E_Multiply(expr1, expr2);
                } else if (op.equals("/")) {
                    expr2 = new E_Divide(expr1, expr2);
                }
            }
            expr1 = expr2;
        }
        return expr1;
    }

    public Expr visitUnaryExpression(RSPQLParser.UnaryExpressionContext ctx) {
        Expr expr = (Expr) ctx.primaryExpression().accept(this);
        if(ctx.getChildCount() > 1) {
            String op = ctx.getChild(0).getText();
            if (op.equals("!")) {
                return new E_LogicalNot(expr);
            } else if(op.equals("+")) {
                return new E_UnaryPlus(expr);
            } else if(op.equals("-")) {
                return new E_UnaryMinus(expr);
            }
        }
        return expr;
    }

    public Expr visitPrimaryExpression(RSPQLParser.PrimaryExpressionContext ctx) {
        if(ctx.brackettedExpression() != null) {
            return (Expr) ctx.brackettedExpression().accept(this);
        }
        if(ctx.builtInCall() != null) {
            return (Expr) ctx.builtInCall().accept(this);
        }
        if(ctx.iriOrFunction() != null) {
            String iri = ctx.iriOrFunction().iri().accept(this).toString();
            if(ctx.iriOrFunction().argList() != null) {
                Args args = (Args) ctx.iriOrFunction().argList().accept(this);
                if (args != null) {
                    return new E_Function(iri, args);
                }
            }
            return ExprUtils.nodeToExpr((Node) ctx.iriOrFunction().iri().accept(this));
        }
        if(ctx.booleanLiteral() != null){
            return ExprUtils.nodeToExpr((Node) ctx.booleanLiteral().accept(this));
        }
        return ExprUtils.nodeToExpr((Node) visitChildren(ctx));
    }

    public Args visitArgList(RSPQLParser.ArgListContext ctx) {
        Args args = new Args();
        if(ctx.distinct() != null){
            args.distinct = true;
        }
        ctx.expression().forEach(a -> {
            args.add((Expr) a.accept(this));
        });
        if(args.size() == 0)
            return null;
        return args;
    }

    public ExprList visitExpressionList(RSPQLParser.ExpressionListContext ctx) {
        ExprList exprList = new ExprList();
        for(RSPQLParser.ExpressionContext i : ctx.expression()){
            Expr expr = (Expr) i.accept(this);
            exprList.add(expr);
        }
        return exprList;
    }

    public Expr visitRegexExpression(RSPQLParser.RegexExpressionContext ctx) {
        Expr expr1 = (Expr) ctx.expression(0).accept(this);
        Expr expr2 = (Expr) ctx.expression(1).accept(this);
        Expr exprFlags = null;
        if(ctx.expression().size() == 3){
            exprFlags = (Expr) ctx.expression(2).accept(this);
        }
        Expr e = new E_Regex(expr1, expr2, exprFlags) ;
        return e;
    }

    public Element visitBind(RSPQLParser.BindContext ctx) {
        Expr expr = (Expr) ctx.expression().accept(this);
        Var var = (Var) ctx.var().accept(this);
        ElementBind el = new ElementBind(var, expr);
        return el;
    }

    public Object visitSubSelect(RSPQLParser.SubSelectContext ctx) {
        Query head = query;
        query = new RSPQLJenaQuery(); // Use regular Jena query if stream queries are not supported in sub-queries
        visitChildren(ctx);
        Element el = new ElementSubQuery(query);
        query = head;
        return el;
    }

    public Object visitLimitClause(RSPQLParser.LimitClauseContext ctx) {
        int i = Integer.parseInt(ctx.INTEGER().getText());
        query.setLimit(i);
        return null;
    }

    public Object visitOffsetClause(RSPQLParser.OffsetClauseContext ctx) {
        int i = Integer.parseInt(ctx.INTEGER().getText());
        query.setOffset(i);
        return null;
    }


    public Expr visitAggregate(RSPQLParser.AggregateContext ctx) {
        String op = ctx.getChild(0).getText().toUpperCase();
        Expr expr;
        Aggregator agg;
        boolean distinct = ctx.distinct() != null;

        switch (op) {
            case "COUNT":
                if(ctx.expression() != null) {
                    expr = (Expr) ctx.expression().accept(this);
                    agg = AggregatorFactory.createCountExpr(distinct, expr);
                } else {
                    agg = AggregatorFactory.createCount(distinct);
                }
                return query.allocAggregate(agg);
            case "SUM":
                expr = (Expr) ctx.expression().accept(this);
                agg = AggregatorFactory.createSum(distinct, expr);
                return query.allocAggregate(agg);
            case "MIN":
                expr = (Expr) ctx.expression().accept(this);
                agg = AggregatorFactory.createMin(distinct, expr);
                return query.allocAggregate(agg);
            case "MAX":
                expr = (Expr) ctx.expression().accept(this);
                agg = AggregatorFactory.createMax(distinct, expr);
                return query.allocAggregate(agg);
            case "AVG":
                expr = (Expr) ctx.expression().accept(this);
                agg = AggregatorFactory.createAvg(distinct, expr);
                return query.allocAggregate(agg);
            case "SAMPLE":
                expr = (Expr) ctx.expression().accept(this);
                agg = AggregatorFactory.createSample(distinct, expr);
                return query.allocAggregate(agg);
            case "GROUP_CONCAT":
                expr = (Expr) ctx.expression().accept(this);
                String sep = ctx.string() != null ? trimQuotes(ctx.string().getText()) : null;
                agg = AggregatorFactory.createGroupConcat(distinct, expr, sep, null);
                return query.allocAggregate(agg);
        }
        return null;
    }

    public Expr visitSubstringExpression(RSPQLParser.SubstringExpressionContext ctx) {
        Expr expr1 = (Expr) ctx.expression(0).accept(this);
        Expr expr2 = (Expr) ctx.expression(1).accept(this);
        Expr expr3 = null;
        if(ctx.expression().size() > 2){
            expr3 = (Expr) ctx.expression(2).accept(this);
        }
        return new E_StrSubstring(expr1, expr2, expr3);
    }

    public Expr visitStrReplaceExpression(RSPQLParser.StrReplaceExpressionContext ctx) {
        Expr expr1 = (Expr) ctx.expression(0).accept(this);
        Expr expr2 = (Expr) ctx.expression(1).accept(this);
        Expr expr3 = (Expr) ctx.expression(2).accept(this);
        Expr expr4 = null;
        if(ctx.expression().size() > 3){
            expr3 = (Expr) ctx.expression(3).accept(this);
        }
        return new E_StrReplace(expr1, expr2, expr3, expr4);
    }

    public Expr visitBuiltInCall(RSPQLParser.BuiltInCallContext ctx) {
        if(ctx.aggregate() != null){
            return (Expr) ctx.aggregate().accept(this);
        }
        else if(ctx.substringExpression() != null){
            return (Expr) ctx.substringExpression().accept(this);
        }
        else if(ctx.strReplaceExpression() != null){
            return (Expr) ctx.strReplaceExpression().accept(this);
        }
        else if(ctx.regexExpression() != null){
            return (Expr) ctx.regexExpression().accept(this);
        }
        else if(ctx.existsFunc() != null){
            return (Expr) ctx.existsFunc().accept(this);
        }
        else if(ctx.notExistsFunc() != null){
            return (Expr) ctx.notExistsFunc().accept(this);
        }

        else if(ctx.expression() != null){
            String op = ctx.getChild(0).getText().toUpperCase();
            Expr expr1, expr2, expr3;
            ExprList exprList;

            switch (op){
                case "STR":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_Str(expr1);
                case "LANG":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_Lang(expr1);
                case "LANGMATCHES":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_LangMatches(expr1, expr2);
                case "DATATYPE":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_Datatype(expr1);
                case "BOUND":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_Bound(expr1);
                case "IRI":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_IRI(expr1);
                case "URI":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_URI(expr1);
                case "BNODE":
                    if(ctx.expression().size() > 0) {
                        expr1 = (Expr) ctx.expression().get(0).accept(this);
                        return new E_BNode(expr1);
                    }
                    return new E_BNode();
                case "RAND":
                    return new E_Random();
                case "ABS":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_NumAbs(expr1);
                case "CEIL":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_NumCeiling(expr1);
                case "FLOOR":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_NumFloor(expr1);
                case "ROUND":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_NumRound(expr1);
                case "CONCAT":
                    exprList = (ExprList) ctx.expressionList().accept(this);
                    return new E_StrConcat(exprList);
                case "STRLEN":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_StrLength(expr1);
                case "UCASE":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_StrUpperCase(expr1);
                case "LCASE":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_StrLowerCase(expr1);
                case "ENCODE_FOR_URI":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_StrEncodeForURI(expr1);
                case "CONTAINS": //2
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrContains(expr1, expr2);
                case "STRSTARTS":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrStartsWith(expr1, expr2);
                case "STRENDS":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrEndsWith(expr1, expr2);
                case "STRBEFORE":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrBefore(expr1, expr2);
                case "STRAFTER":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrAfter(expr1, expr2);
                case "YEAR":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeYear(expr1);
                case "MONTH":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeMonth(expr1);
                case "DAY":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeDay(expr1);
                case "HOURS":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeHours(expr1);
                case "MINUTES":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeMinutes(expr1);
                case "SECONDS":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeSeconds(expr1);
                case "TIMEZONE":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeTimezone(expr1);
                case "TZ":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_DateTimeTZ(expr1);
                case "NOW":
                    return new E_Now();
                case "UUID":
                    return new E_UUID();
                case "STRUUID":
                    return new E_StrUUID();
                case "MD5":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_MD5(expr1);
                case "SHA1":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_SHA1(expr1);
                case "SHA256":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_SHA256(expr1);
                case "SHA384":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_SHA384(expr1);
                case "SHA512":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_SHA512(expr1);
                case "COALESCE":
                    exprList = (ExprList) ctx.expressionList().accept(this);
                    return new E_Coalesce(exprList);
                case "IF":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    expr3 = (Expr) ctx.expression().get(2).accept(this);
                    return new E_Conditional(expr1, expr2, expr3);
                case "STRLANG":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrLang(expr1, expr2);
                case "STRDT":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_StrDatatype(expr1, expr2);
                case "SAMETERM":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    expr2 = (Expr) ctx.expression().get(1).accept(this);
                    return new E_SameTerm(expr1, expr2);
                case "ISIRI":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_IsIRI(expr1);
                case "ISURI":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_URI(expr1);
                case "ISBLANK":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_IsBlank(expr1);
                case "ISLITERAL":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_IsLiteral(expr1);
                case "ISNUMERIC":
                    expr1 = (Expr) ctx.expression().get(0).accept(this);
                    return new E_IsNumeric(expr1);
            }
        }
        return null;
    }

    public String trimTags(String s){
        return s.replaceAll("^<(.*)>$", "$1");
    }

    public String trimQuotes(String s){
        return s.replaceAll("^['\"](.*)['\"]$", "$1");
    }

    public String trimFirst(String s){
        return s.replaceAll("^.(.*)$", "$1");
    }

    public String trimLast(String s){
        return s.replaceAll("^(.*).$", "$1");
    }
}

