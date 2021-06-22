package org.streamreasoning.rsp4j.abstraction.triplepattern;

import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.jena.JenaRDF;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.sse.SSE;
import org.apache.jena.sparql.util.FmtUtils;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMappingBase;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TriplePatternR2R implements RelationToRelationOperator<Graph, Binding> {
    private final ContinuousTriplePatternQuery query;

    public TriplePatternR2R(ContinuousTriplePatternQuery query) {
        this.query = query;
    }

    @Override
    public Stream<Binding> eval(Stream<Graph> sds) {
        Model dataModel = convertToJenaModel(sds);

        return evaluateQuery(dataModel, System.currentTimeMillis());
    }

    @Override
    public TimeVarying<Collection<Binding>> apply(SDS<Graph> sds) {
        return null;
    }

    @Override
    public SolutionMapping<Binding> createSolutionMapping(Binding result) {
        return new SolutionMappingBase<>(result,System.currentTimeMillis());
        //return new TableResponse(query.getID() + "/ans/" + System.currentTimeMillis(), System.currentTimeMillis(),result);
    }

    public TimeVarying<Collection<Binding>> apply() {
        //TODO
        return null;
    }

    private Model convertToJenaModel(Stream<Graph> sds) {
        RDF instance = RDFUtils.getInstance();
        JenaRDF jena = new JenaRDF();
        String stream_uri = query.getStreamURI();
        IRI stream = instance.createIRI(stream_uri);

        Graph materializedGraph = RDFUtils.createGraph();
        sds.forEach( graph->{graph.stream().forEach(materializedGraph::add);});
        org.apache.jena.graph.Graph jenaGraph = jena.asJenaGraph(materializedGraph);
        Model dataModel = ModelFactory.createModelForGraph(jenaGraph);
        return dataModel;
    }

    private Stream<Binding> evaluateQuery(Model dataModel, long ts) {
        String queryString = String.format("(bgp (%s))", query.getTriplePattern());
        Op op = SSE.parseOp(queryString);
        QueryIterator qIter = Algebra.exec(op, dataModel);
        List<Var> vars = getVariableFromQuery().stream().map(v -> Var.alloc(v)).collect(Collectors.toList());
        Binding binding = new BindingImpl();
        List<Binding> bindings = new ArrayList<>();
        getVariableFromQuery();
        for (; qIter.hasNext(); ) {
            org.apache.jena.sparql.engine.binding.Binding b = qIter.nextBinding();
            for (Var v : vars) {
                Node n = b.get(v);
                binding.add(new VarImpl(v.getVarName()), RDFUtils.createIRI(RDFUtils.trimTags(FmtUtils.stringForNode(n))));
                bindings.add(binding);
            }
        }
        qIter.close();
        return bindings.stream();
    }

    private List<String> getVariableFromQuery() {
        String queryString = query.getTriplePattern();
        Pattern pattern = Pattern.compile("\\?([a-zA-Z0-9]+)");
        Matcher matcher = pattern.matcher(queryString);
        List<String> queryVariables = new ArrayList<>();
        while (matcher.find()) {
            queryVariables.add(matcher.group(1));
        }
        return queryVariables;
    }
}
