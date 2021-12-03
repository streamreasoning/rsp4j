package org.streamreasoning.rsp4j.reasoning.datalog;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.Collection;
import java.util.stream.Stream;

public class DatalogR2R implements RelationToRelationOperator<Graph,Graph> {

    private final DatalogProgram datalog;

    public DatalogR2R(){
        this.datalog = new DatalogProgram();
    }
    public DatalogR2R(DatalogProgram datalogProgram){
        this.datalog = datalogProgram;
    }
    @Override
    public Stream<Graph> eval(Stream<Graph> sds) {
        datalog.reset();
        // add data to the reasoner
        sds.forEach(g -> datalog.addFacts(g));
        // materialize
        datalog.materialize();
        // create result graph
        Graph materializedGraph = RDFUtils.createGraph();
        //retrieve the data and convert to common triples
        datalog.getFacts().stream()
                .map(t->convertToTriple(t))
                .forEach(t->materializedGraph.add(t));
        return Stream.of(materializedGraph);
    }
    private Triple convertToTriple(ReasonerTriple triple){
        return RDFUtils.createTriple(triple.getSubject(),triple.getProperty(),triple.getObject());
    }
    @Override
    public TimeVarying<Collection<Graph>> apply(SDS<Graph> sds) {
        return null;
    }

    @Override
    public SolutionMapping<Graph> createSolutionMapping(Graph result) {
        return null;
    }
    public void addFact(ReasonerTriple t) {
        datalog.addFact(t);
    }
    public void addFact(Triple t) {
        datalog.addFact(t);
    }
    public void addFacts(Graph g) {
       datalog.addFacts(g);
    }

    public void addRule(Rule r) {
        datalog.addRule(r);
    }


}
