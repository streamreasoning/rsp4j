package org.streamreasoning.rsp4j.yasper.querying.syntax;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLBaseVisitor;
import org.streamreasoning.rsp4j.api.querying.syntax.RSPQLParser;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;

import java.util.*;

public class TPVisitorImpl extends RSPQLBaseVisitor<CQ> {

    private VarOrTerm s;
    private VarOrTerm p;
    private VarOrTerm o;
    private String outputStreamIRI;
    private Map<String, WindowNode> windowMap;
    private String outputStreamType;

    private List<Aggregation> aggregations;
    public TPVisitorImpl() {
        windowMap = new HashMap<>();
        aggregations = new ArrayList<>();
    }

    @Override
    public CQ visitTriplesTemplate(RSPQLParser.TriplesTemplateContext ctx) {
        RSPQLParser.TriplesSameSubjectContext t = ctx.triplesSameSubject();
        RSPQLParser.VarOrTermContext s = t.s;
        RSPQLParser.PropertyListNotEmptyContext po = t.ps;
        return super.visitTriplesTemplate(ctx);
    }

    @Override
    public CQ visitTriplesBlock(RSPQLParser.TriplesBlockContext ctx) {

        return super.visitTriplesBlock(ctx);
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
    public CQ visitTriplesSameSubjectPath(RSPQLParser.TriplesSameSubjectPathContext ctx) {
        extractSubject(ctx.s);
        extractSinglePropertyObjectPair(ctx.ps);

        return super.visitTriplesSameSubjectPath(ctx);
    }

    private void extractSubject(RSPQLParser.VarOrTermContext varOrTerm) {
        RSPQLParser.VarContext var = varOrTerm.var();
        RSPQLParser.GraphTermContext term = varOrTerm.graphTerm();
        if (var != null) {
            s = createVar(var.getText());
        } else {
            s = createTerm(term.getText());
        }
    }

    private void extractSinglePropertyObjectPair(RSPQLParser.PropertyListPathNotEmptyContext po) {
        RSPQLParser.PropertyListPathContext pCandidate = po.propertyListPath(0);
        if (pCandidate != null) {
            extractProperty(pCandidate);
            RSPQLParser.ObjectPathContext object = pCandidate.objectListPath().objectPath(0);
            if (object != null) {
                extractObject(object);
            }
        }
    }

    private void extractProperty(RSPQLParser.PropertyListPathContext propCandidate) {
        if (propCandidate.verbPath() != null) {
            p = createTerm(propCandidate.verbPath().getText());
        } else {
            p = createVar(propCandidate.verbSimple().getText());
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
        if (object.graphNodePath().varOrTerm().var() != null) {
            o = createVar(object.graphNodePath().varOrTerm().var().getText());
        } else {
            o = createTerm(object.graphNodePath().varOrTerm().graphTerm().getText());
        }
    }


    public ContinuousQuery<Graph, Graph, Binding, Binding> generateQuery() {

        DataStream stream = null;
        WindowNode win = null;
        Optional<Map.Entry<String, WindowNode>> window = windowMap.entrySet().stream().findFirst();
        if (window.isPresent()) {
            stream = new DataStreamImpl(window.get().getKey());
            win = window.get().getValue();

        }

        Rstream<Binding, Binding> rstream = new Rstream<>();
        SimpleRSPQLQuery<Binding> query = new SimpleRSPQLQuery<>("", stream, win, s, p, o, rstream);
        windowMap.entrySet().forEach(e -> query.addNamedWindow(e.getKey(), e.getValue()));
        if (outputStreamType != null) {
            RSPQLExtractionHelper.setOutputStreamType(query, outputStreamType);
        }
        query.setOutputStream(outputStreamIRI);
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
        for(RSPQLParser.ResultVarContext  r:ctx.selectClause().resultVar()){
            String var = r.var().getText();
            String exp = r.expression().getText();
            aggregations.add(new Aggregation(null,getVarName(exp),var,getFunctionName(exp)));
        }
        return super.visitSelectQuery(ctx);
    }
    private String getFunctionName(String expression){
        return expression.substring(0,expression.indexOf('('));
    }
    private String getVarName(String expression){
        return expression.substring(expression.indexOf('?'),expression.indexOf(')'));
    }

    /**
     * Visit window definition clauses. For now we support only  logical windows
     *
     * @param ctx
     * @return
     */
    public CQ visitNamedWindowClause(RSPQLParser.NamedWindowClauseContext ctx) {
        Map.Entry<String, WindowNode> streamWindowPair = RSPQLExtractionHelper.extractNamedWindowClause(ctx);
        windowMap.put(streamWindowPair.getKey(), streamWindowPair.getValue());

        return super.visitNamedWindowClause(ctx);
    }


}
