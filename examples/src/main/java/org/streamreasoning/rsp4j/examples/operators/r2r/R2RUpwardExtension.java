package org.streamreasoning.rsp4j.examples.operators.r2r;


import org.apache.commons.rdf.api.*;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.SelectInstResponse;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class R2RUpwardExtension implements RelationToRelationOperator<Graph,Graph> {


    private Map<String, Set<String>> extensions;

    private final IRI RDFTYPE= RDFUtils.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");

    public R2RUpwardExtension() {
        this.extensions = new HashMap<String, Set<String>>();

    }

    public R2RUpwardExtension(Map<String, List<String>> schema) {
        this();
        addSchema(schema);
    }

    public void addSchema(Map<String, List<String>> schema) {
        // 1 To extract the top parents, we extract all concepts and remove those that have parents:
        // 1.1 get all childeren
        Set<String> childeren = new HashSet<String>();
        for (List<String> childs : schema.values()) {
            childeren.addAll(childs);
        }
        // 1.2. get all the types
        Set<String> all = new HashSet<String>(childeren);
        all.addAll(schema.keySet());
        Set<String> tops = new HashSet(all);
        // 1.2 remove the childeren from all the types and the parents remain
        tops.removeAll(childeren);
        // 2 recursively follow the hierarchy from the top parents and build the inference structure
        for (String top : tops) {
            if (!this.extensions.containsKey(top)) {
                this.extensions.put(top, new HashSet<>());
            }
            findSubclasses(top, schema);
        }
    }

    private void findSubclasses(String parent, Map<String, List<String>> schema) {
        if (schema.containsKey(parent)) {
            for (String child : schema.get(parent)) {
                if (!this.extensions.containsKey(child)) {
                    this.extensions.put(child, new HashSet<>());
                }
                // add all the parent concepts of the current parent to the extensions
                this.extensions.get(child).addAll(this.extensions.get(parent));
                this.extensions.get(child).add(parent);
                // recursive step
                findSubclasses(child, schema);
            }
        }
    }

    @Override
    public Stream<Graph> eval(Stream<Graph> sds) {
        RDF instance = RDFUtils.getInstance();

        Set<Graph> sol = new HashSet<Graph>();
        // iterate over the triples in the SDS
        for(Graph g : sds.collect(Collectors.toList())){
            Graph upwardGraph = RDFUtils.createGraph();
        for (Triple t : g.stream().collect(Collectors.toList())) {
            // check if triple is a type assertion
            if (t.getPredicate().equals(instance.createIRI("a")) || t.getPredicate().equals(RDFTYPE)) {
                //reasoning step
                String type = t.getObject().toString();
                type = type.substring(1, type.length() - 1);
                if (this.extensions.containsKey(type)) {
                    // extract the parent concepts
                    for (String parents : this.extensions.get(type)) {
                        // create a new triple (the materialization)
                        Triple reasoningResult = instance.createTriple(t.getSubject(), RDFTYPE, instance.createIRI(parents));
                        // add the materialization to the solution set
//                       sol.add(new SelectInstResponse(query.getID() + "/ans/" + ts, ts, reasoningResult));s
                        upwardGraph.add(reasoningResult);
                    }
                }

            }else{
                upwardGraph.add(t);
            }
            //add the triple
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
