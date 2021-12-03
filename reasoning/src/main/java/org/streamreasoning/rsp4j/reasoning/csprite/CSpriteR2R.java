package org.streamreasoning.rsp4j.reasoning.csprite;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BGP;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TP;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSpriteR2R implements RelationToRelationOperator<Graph, Graph>{
    private final HierarchySchema hierarchySchema;
    private final RelationToRelationOperator<Graph, Binding> r2r;
    private final UpwardExtension upwardExtension;
    private final R2RUpwardExtension r2rUpward;
    private Set<String> queriedTypes;
    private Set<String> queriedProperties;
    public CSpriteR2R(RelationToRelationOperator<Graph, Binding> relationOperator, HierarchySchema hierarchySchema) {
        this.r2r = relationOperator;
        this.hierarchySchema = hierarchySchema;
        this.queriedTypes = new HashSet<>();
        this.queriedProperties = new HashSet<>();
        upwardExtension = new UpwardExtension(hierarchySchema.getSchema());
        this.findTypes();
        this.pruneHierarchy();
        this.r2rUpward = new R2RUpwardExtension(this.upwardExtension);
    }

    public void findTypes() {
        if(r2r instanceof TP){
            TP tp = (TP)r2r;
            extractTPTypes(tp);

        }else if(r2r instanceof BGP){
            BGP bgp = (BGP) r2r;
            bgp.getTPs().forEach(tp->extractTPTypes(tp));
        }
    }

    private void extractTPTypes(TP tp) {
        if(tp.getProperty().toString().equals(RDFUtils.RDFTYPE.toString()) && tp.getObject().isTerm()){
            queriedTypes.add(tp.getObject().getIRIString());
        }
        if(tp.getProperty().toString().equals(RDFUtils.RDFTYPE.toString()) && tp.getObject().isVariable()){ // all types are being queried
            List<String> childs = hierarchySchema.getSchema().values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            queriedTypes.addAll(childs);
            queriedTypes.addAll(hierarchySchema.getSchema().keySet());
        }
    }

    public void pruneHierarchy() {
        Map<String, Set<String>> upwardExtensions = upwardExtension.getExtensions();
        Map<String, Set<String>> result =
            upwardExtensions.entrySet().stream()
                .filter(
                    e -> {
                      e.getValue().retainAll(this.queriedTypes);
                      return !e.getValue().isEmpty();
                    })
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        upwardExtension.setExtensions(result);
    }
    public Map<String, Set<String>> getHierachy(){
        return upwardExtension.getExtensions();
    }
    public Set<String> getQueriedTypes() {
        return queriedTypes;
    }

    public Set<String> getQueriedProperties() {
        return queriedProperties;
    }

    @Override
    public Stream<Graph> eval(Stream<Graph> sds) {
        return r2rUpward.eval(sds);
    }

    @Override
    public TimeVarying<Collection<Graph>> apply(SDS<Graph> sds) {
        return r2rUpward.apply(sds);
    }

    @Override
    public SolutionMapping<Graph> createSolutionMapping(Graph result) {
        return r2rUpward.createSolutionMapping(result);
    }
}
