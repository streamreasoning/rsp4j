package org.streamreasoning.rsp4j.examples.operators.r2r;


import it.polimi.deib.sr.rsp.api.RDFUtils;
import it.polimi.deib.sr.rsp.api.operators.r2r.RelationToRelationOperator;
import it.polimi.deib.sr.rsp.api.querying.ContinuousQuery;
import it.polimi.deib.sr.rsp.api.querying.result.SolutionMapping;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.yasper.querying.SelectInstResponse;
import org.apache.commons.rdf.api.Dataset;
import org.apache.commons.rdf.api.Quad;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.api.RDF;


import java.util.*;
import java.util.stream.Stream;

public class R2RUpwardExtension implements RelationToRelationOperator<Triple> {

    private final SDS sds;
    private final ContinuousQuery query;
    private final Dataset ds;
    private Map<String, Set<String>> extensions;


    public R2RUpwardExtension(SDS sds, ContinuousQuery query) {
        this.sds = sds;
        this.query = query;
        this.ds = (Dataset) sds;
        this.extensions = new HashMap<String,Set<String>>();
        Map<String,List<String>> schema = new HashMap<>();
        schema.put("O2",Arrays.asList("O1","O4"));
        schema.put("O3",Arrays.asList("O2","O5"));
        schema.put("O5",Arrays.asList("O6"));
        this.addSchema(schema);
        System.out.println(this.extensions);

    }
    public void addSchema(Map<String,List<String>> schema){
        // 1 To extract the top parents, we extract all concepts and remove those that have parents:
        // 1.1 get all childeren
        Set<String> childeren = new HashSet<String>();
        for(List<String> childs : schema.values()){
            childeren.addAll(childs);
        }
        // 1.2. get all the types
        Set<String> all = new HashSet<String>(childeren);
        all.addAll(schema.keySet());
        Set<String> tops =   new HashSet(all);
        // 1.2 remove the childeren from all the types and the parents remain
        tops.removeAll(childeren);
        // 2 recursively follow the hierarchy from the top parents and build the inference structure
        for(String top : tops){
            if (!this.extensions.containsKey(top)) {
                this.extensions.put(top, new HashSet<>());
            }
            findSubclasses(top,schema);
        }
    }
    private void findSubclasses(String parent,Map<String,List<String>> schema){
        if(schema.containsKey(parent) ){
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
    public Stream<SolutionMapping<Triple>> eval(long ts) {
        RDF instance = RDFUtils.getInstance();
        Set<SolutionMapping<Triple>> sol = new HashSet<SolutionMapping<Triple>>();
        // iterate over the triples in the SDS
        for(Quad q : ds.iterate()){
           Triple t =  q.asTriple();
           // check if triple is a type assertion
           if(t.getPredicate().equals(instance.createIRI("a"))){
               //reasoning step
               String type = t.getObject().toString();
               type = type.substring(1,type.length()-1);
               if(this.extensions.containsKey(type)){
                   // extract the parent concepts
                   for(String parents:this.extensions.get(type)){
                       // create a new triple (the materialization)
                       Triple reasoningResult = instance.createTriple(t.getSubject(),instance.createIRI("a"),instance.createIRI(parents));
                       // add the materialization to the solution set
                       sol.add(new SelectInstResponse(query.getID() + "/ans/" + ts, ts, reasoningResult));
                   }
               }

           }
           //add the triple
            sol.add(new SelectInstResponse(query.getID() + "/ans/" + ts, ts, t));
        }
        return sol.stream();

    }
}
