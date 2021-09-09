package org.streamreasoning.rsp4j.csparql;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2s.RelationToStreamOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.Aggregation;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSPARQLAbstractQuery<O> implements ContinuousQuery<Graph, Graph,Binding,O> {

    private final CsparqlQueryResultProxy proxy;
    private final Query query;

    public CSPARQLAbstractQuery(CsparqlQueryResultProxy proxy, String sparqlQuery) {
        query = QueryFactory.create(sparqlQuery, Syntax.syntaxSPARQL_11);
        this.proxy = proxy;

    }

    public List<String> getVariables(){
        return query.getProjectVars().stream()
                .map(var -> var.getVarName())
                .collect(Collectors.toList());
    }

    @Override
    public void addNamedWindow(String streamUri, WindowNode wo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIstream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRstream() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setDstream() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isIstream() {
        return false;
    }

    @Override
    public boolean isRstream() {
        return true;
    }

    @Override
    public boolean isDstream() {
        return false;
    }

    @Override
    public void setSelect() {
        throw new UnsupportedOperationException();

    }

    @Override
    public void setConstruct() {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean isSelectType() {
        return query.isSelectType();
    }

    @Override
    public boolean isConstructType() {
        return query.isConstructType();
    }



    @Override
    public void setOutputStream(String uri) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String getID() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<? extends WindowNode, DataStream<Graph>> getWindowMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Time getTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RelationToRelationOperator<Graph, Binding> r2r() {
        throw new UnsupportedOperationException();
    }

    @Override
    public StreamToRelationOp<Graph, Graph>[] s2r() {
        return new StreamToRelationOp[0];
    }



    @Override
    public List<Aggregation> getAggregations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public RelationToStreamOperator<Binding, O> r2s() {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataStream<O> getOutputStream() {
        throw new UnsupportedOperationException();
    }
}
