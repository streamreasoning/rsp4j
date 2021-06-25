package org.streamreasoning.rsp4j.examples.operators.r2r;


import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class R2RUpwardExtension implements RelationToRelationOperator<Graph, Graph> {


    private final IRI RDFTYPE = RDFUtils.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    private UpwardExtension extension;


    public R2RUpwardExtension(Map<String, List<String>> schema) {
        extension = new UpwardExtension(schema);

    }
    public R2RUpwardExtension(UpwardExtension extension) {
        this.extension = extension;

    }

    @Override
    public Stream<Graph> eval(Stream<Graph> tvg) {
        RDF instance = RDFUtils.getInstance();

        Set<Graph> sol = new HashSet<Graph>();
        // iterate over the triples in the SDS
        for (Graph g : tvg.collect(Collectors.toList())) {
            Graph upwardGraph = RDFUtils.createGraph();
            for (Triple t : g.stream().collect(Collectors.toList())) {
                // add the current triple
                upwardGraph.add(t);
                // check if triple is a type assertion
                if (t.getPredicate().equals(instance.createIRI("a")) || t.getPredicate().equals(RDFTYPE)) {
                    //reasoning step
                    String type = t.getObject().ntriplesString();
                    type = RDFUtils.trimTags(type);
                        // extract the parent concepts
                        for (String parents : extension.getUpwardExtension(type)) {
                            // create a new triple (the materialization)
                            Triple reasoningResult = instance.createTriple(t.getSubject(), RDFTYPE, instance.createIRI(parents));
                            // add the materialization to the solution set
//                       sol.add(new SelectInstResponse(query.getID() + "/ans/" + ts, ts, reasoningResult));s
                            upwardGraph.add(reasoningResult);
                        }
                }
                //add to the solution
                sol.add(upwardGraph);
            }
        }

        return sol.stream();

    }


    @Override
    public TimeVarying<Collection<Graph>> apply(SDS<Graph> sds) {
        return null;
    }

    @Override
    public SolutionMapping<Graph> createSolutionMapping(Graph result) {
        return null;
    }
}
