package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDFTerm;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.Var;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLBaseVisitor;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;

import java.util.*;
import java.util.function.Predicate;

public class TPVisitorImpl extends RSPQLBaseVisitor<CQ> {


    private String outputStreamIRI;
    private Map<String, List<WindowNode>> windowMap;
    private String outputStreamType;
    private Time time;
    private List<Aggregation> aggregations;
    private String windowIRI = "default";
    Stack<TripleHolder> triples;
    Map<String,List<TripleHolder>> windowsToTriples;
    Map<String,List<Predicate<Binding>>> windowsToFilters;
    private String defaultGraphUri;
    private List<Var> projections;

    public TPVisitorImpl() {
        windowMap = new HashMap<>();
        aggregations = new ArrayList<>();
        this.time = new TimeImpl(0);
        triples = new Stack<TripleHolder>();
        windowsToTriples = new HashMap<>();
        windowsToFilters = new HashMap<>();
        projections = new ArrayList<>();
    }

    @Override
    public CQ visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx) {
        RSPQLParser.TriplesSameSubjectContext t = ctx.triplesSameSubject();
        RSPQLParser.VarOrTermContext s = t.s;
        RSPQLParser.PropertyListNotEmptyContext po = t.ps;
        return super.visitTriplesTemplate(ctx);
    }

    @Override
    public CQ visitGroupGraphPattern(RSPQLParser.GroupGraphPatternContext ctx) {
        return super.visitGroupGraphPattern(ctx);
    }

    @Override
    public CQ visitGroupGraphPatternSub(RSPQLParser.GroupGraphPatternSubContext ctx) {
        return super.visitGroupGraphPatternSub(ctx);
    }

    @Override
    public CQ visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx) {

        return super.visitTriplesBlock(ctx);
    }

    @Override
    public CQ visitGraphPatternNotTriples(RSPQLParser.GraphPatternNotTriplesContext ctx) {
        return super.visitGraphPatternNotTriples(ctx);
    }

    @Override
    public CQ visitGraphGraphPattern(RSPQLParser.GraphGraphPatternContext ctx) {
        return super.visitGraphGraphPattern(ctx);
    }

    @Override
    public CQ visitWindowGraphPattern(RSPQLParser.WindowGraphPatternContext ctx) {
        windowIRI=RDFUtils.trimTags(ctx.varOrIri().iri().getText());
        windowsToTriples.put(windowIRI,new ArrayList<>());
        return super.visitWindowGraphPattern(ctx);
    }

    @Override
    public CQ visitTriplesSameSubject(RSPQLParser.TriplesSameSubjectContext ctx) {
        return super.visitTriplesSameSubject(ctx);
    }

    @Override
    public CQ visitPropertyListNotEmpty(RSPQLParser.PropertyListNotEmptyContext ctx) {
        return super.visitPropertyListNotEmpty(ctx);
    }

    @Override
    public CQ visitPropertyList(RSPQLParser.PropertyListContext ctx) {

        return super.visitPropertyList(ctx);
    }

    @Override
    public CQ visitObjectList(RSPQLParser.ObjectListContext ctx) {

        return super.visitObjectList(ctx);
    }

    @Override
    public CQ visitObject(RSPQLParser.ObjectContext ctx) {

        return super.visitObject(ctx);
    }
    @Override
    public CQ visitDefaultGraphClause(RSPQLParser.DefaultGraphClauseContext ctx) {
        this.defaultGraphUri = RDFUtils.trimTags(ctx.sourceSelector().iri().IRIREF().getText());
        windowsToTriples.put("default", new ArrayList<>());
        return super.visitDefaultGraphClause(ctx);
    }

    @Override
    public CQ visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx) {
        triples.push(new TripleHolder());
        if(!windowsToTriples.containsKey(windowIRI)){
            windowsToTriples.put(windowIRI,new ArrayList<>());
        }
        windowsToTriples.get(windowIRI).add(triples.peek());
        extractSubject(ctx.s);
        extractSinglePropertyObjectPair(ctx.ps);

        return super.visitTriplesSameSubjectPath(ctx);
    }

    private void extractSubject(RSPQLParser.VarOrTermContext varOrTerm) {
        RSPQLParser.VarContext var = varOrTerm.var();
        RSPQLParser.GraphTermContext term = varOrTerm.graphTerm();
        if (var != null) {
            triples.peek().s = createVar(var.getText());
        } else {
            triples.peek().s = createTerm(term.getText());
        }
    }

    private void extractSinglePropertyObjectPair(RSPQLParser.PropertyListPathNotEmptyContext po) {
        for (RSPQLParser.PropertyListPathContext pCandidate : po.propertyListPath()) {
          if (pCandidate != null) {
            extractProperty(pCandidate);
            for(RSPQLParser.ObjectPathContext object : pCandidate.objectListPath().objectPath()){
                if (object != null) {
                  extractObject(object);
                }
            }
          }
        }
    }

    private void extractProperty(RSPQLParser.PropertyListPathContext propCandidate) {
        if(triples.peek().p != null){
            VarOrTerm s = triples.peek().s;
            triples.push(new TripleHolder());
            windowsToTriples.get(windowIRI).add(triples.peek());

            triples.peek().s=s;
        }
        if (propCandidate.verbPath() != null) {
            triples.peek().p = createTerm(propCandidate.verbPath().getText());
        } else {
            triples.peek().p = createVar(propCandidate.verbSimple().getText());
        }
    }

    private VarImpl createVar(String varName) {
        if (varName.startsWith("?")) {
            varName = RDFUtils.trimFirst(varName);
        }
        return new VarImpl(varName);
    }

    private TermImpl createTerm(String textIRI) {
        textIRI = RDFUtils.trimTags(textIRI);
        return new TermImpl(RDFUtils.createIRI(textIRI));
    }

    private void extractObject(RSPQLParser.ObjectPathContext object) {
        if(triples.peek().o != null){
            VarOrTerm s = triples.peek().s;
            VarOrTerm p = triples.peek().p;
            triples.push(new TripleHolder());
            windowsToTriples.get(windowIRI).add(triples.peek());

            triples.peek().s=s;
            triples.peek().p=p;
        }
        if (object.graphNodePath().varOrTerm().var() != null) {
            triples.peek().o = createVar(object.graphNodePath().varOrTerm().var().getText());
        } else {
            triples.peek().o = createTerm(object.graphNodePath().varOrTerm().graphTerm().getText());
        }
    }


    public ContinuousQuery<Graph, Graph, Binding, Binding> generateQuery() {

        DataStream stream = null;
        WindowNode win = null;
        Optional<Map.Entry<String, List<WindowNode>>> window = windowMap.entrySet().stream().findFirst();
        if (window.isPresent()) {
            stream = new DataStreamImpl(window.get().getKey());
            win = window.get().getValue().get(0);

        }

        Rstream<Binding, Binding> rstream = new Rstream<>();
        SimpleRSPQLQuery<Binding> query = new SimpleRSPQLQuery<>(windowIRI, stream, time, win, windowsToTriples, rstream, defaultGraphUri);
        windowMap.entrySet().forEach(e ->
                e.getValue().forEach(w->query.addNamedWindow(e.getKey(), w)));
        if (outputStreamType != null) {
            RSPQLExtractionHelper.setOutputStreamType(query, outputStreamType);
        }
        query.getProjections().addAll(projections);
        query.setOutputStream(outputStreamIRI);
        query.addFiltersIfDefined(windowsToFilters);
        //add aggregations
        query.getAggregations().addAll(aggregations);
        return query;
    }

    /**
     * Set output stream type (ISTREAM, DSTREAM or RSTREAM)
     *
     * @param ctx
     * @return
     */
    public CQ visitOutputStreamType(RSPQLParser.OutputStreamTypeContext ctx) {
        this.outputStreamType = ctx.getText();

        return super.visitOutputStreamType(ctx);
    }

    /**
     * Set output stream URI
     *
     * @param ctx
     * @return
     */
    public CQ visitOutputStream(RSPQLParser.OutputStreamContext ctx) {
        outputStreamIRI = RSPQLExtractionHelper.extractOutputStream(ctx);

        return super.visitOutputStream(ctx);
    }

    @Override
    public CQ visitSelectQuery(RSPQLParser.SelectQueryContext ctx) {
        for (RSPQLParser.ResultVarContext r : ctx.selectClause().resultVar()) {

            String var = r.var().getText();
            if (r.expression() != null) {
                String exp = r.expression().getText();
                aggregations.add(new Aggregation(null, getVarName(exp), var, getFunctionName(exp)));
            }else{
                projections.add(new VarImpl(RDFUtils.trimVar(var)));
            }
        }
        return super.visitSelectQuery(ctx);
    }


    private String getFunctionName(String expression) {
        return expression.substring(0, expression.indexOf('('));
    }

    private String getVarName(String expression) {
        return expression.substring(expression.indexOf('?'), expression.indexOf(')'));
    }

    /**
     * Visit window definition clauses. For now we support only  logical windows
     *
     * @param ctx
     * @return
     */
    public CQ visitNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx) {
        Map.Entry<String, WindowNode> streamWindowPair = RSPQLExtractionHelper.extractNamedWindowClause(ctx);
        if(!windowMap.containsKey(streamWindowPair.getKey())){
            windowMap.put(streamWindowPair.getKey(),new ArrayList<WindowNode>());
        }
        windowMap.get(streamWindowPair.getKey()).add( streamWindowPair.getValue());

        return super.visitNamedWindowClause(ctx);
    }
    @Override
    public CQ visitFilter(RSPQLParser.FilterContext ctx) {
        Predicate<Binding> orPredicate = null;
        for(RSPQLParser.ConditionalAndExpressionContext andExpression :ctx.constraint().brackettedExpression().expression().conditionalOrExpression().conditionalAndExpression()){
            Predicate<Binding> andPredicate = null;
            for(RSPQLParser.ValueLogicalContext val :andExpression.valueLogical()){
                RSPQLParser.RelationalExpressionContext expression = val.relationalExpression();
                String expression1 = expression.numericExpression(0).getText();
                String expression2 = expression.numericExpression(1).getText();
                String logicalOperator = expression.getChild(1).getText();
                Predicate<Binding> p = null;
                if (!isValue(expression1) && !isValue(expression2)) {
                  p = createPredicateWithoutValues(expression1, expression2, logicalOperator);
                }else{
                    p = createPredicateWithValues(expression1, expression2, logicalOperator);
                }
                if(andPredicate!=null && p!=null){
                    andPredicate= andPredicate.and(p);
                }else if(p!=null){
                    andPredicate = p;
                }
            }
            if(andPredicate!=null && orPredicate!=null){
                orPredicate= orPredicate.or(andPredicate);
            }else if(andPredicate!=null){
                orPredicate = andPredicate;
            }
        }
        if(orPredicate!=null){
            windowsToFilters.computeIfAbsent(windowIRI,s->new ArrayList<>()).add(orPredicate);
        }
        return visitChildren(ctx);
    }

    private Predicate<Binding> createPredicateWithValues(String expression1, String expression2, String logicalOperator) {
        Predicate<Binding> p = null;
        switch (logicalOperator){
            case "=":
                p = b-> parseOrRetrieveDataFromBinding(b, expression1).equals(parseOrRetrieveDataFromBinding(b, expression2));
                break;
            case "!=":
                p = b-> !parseOrRetrieveDataFromBinding(b, expression1).equals(parseOrRetrieveDataFromBinding(b, expression2));
                break;
            case "<=":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) <= parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case ">=":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) >= parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case ">":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) > parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case "<":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) < parseOrRetrieveDataFromBinding(b,expression2);
                break;
        }
        return p;
    }

    private Double parseOrRetrieveDataFromBinding(Binding b, String expression){
        if(isValue(expression)){
            return Double.parseDouble(expression) ;
        }else{
            return RDFUtils.parseDouble(b.value(new VarImpl(expression)).ntriplesString());
        }
    }
    private Predicate<Binding> createPredicateWithoutValues(String expression1, String expression2, String logicalOperator) {
        VarOrTerm var1 = convertToVarOrTerm(expression1);
        VarOrTerm var2 = convertToVarOrTerm(expression2);
        Predicate<Binding> p = null;
        switch(logicalOperator){
            case "=":
                p = b-> getBindingValue(b,var1).equals(getBindingValue(b,var2));
                break;
            case "!=":
                p = b-> !getBindingValue(b,var1).equals(getBindingValue(b,var2));
                break;
            case "<=":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) <= parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case ">=":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) >= parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case ">":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) > parseOrRetrieveDataFromBinding(b,expression2);
                break;
            case "<":
                p = b-> parseOrRetrieveDataFromBinding(b,expression1) < parseOrRetrieveDataFromBinding(b,expression2);
                break;
        }
        return p;
    }

    private boolean isValue(String expression){
        try{
            Double.parseDouble(expression);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    private RDFTerm getBindingValue(Binding b, VarOrTerm varOrTerm){
        if(varOrTerm instanceof VarImpl){
            return b.value(varOrTerm);
        }else{
            return varOrTerm;
        }
    }
    private VarOrTerm convertToVarOrTerm(String varOrTerm){
        if(varOrTerm.startsWith("?")){
            return new VarImpl(varOrTerm);
        }else{
            return new TermImpl(RDFUtils.trimTags(varOrTerm));
        }
    }


}
