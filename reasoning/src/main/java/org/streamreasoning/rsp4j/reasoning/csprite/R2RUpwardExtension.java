package org.streamreasoning.rsp4j.reasoning.csprite;


import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.querying.result.SolutionMapping;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.utils.TripleCollector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class R2RUpwardExtension implements RelationToRelationOperator<Graph, Graph> {


    private final IRI RDFTYPE = RDFUtils.RDFTYPE;
    private UpwardExtension extension;
    private final RDF instance = RDFUtils.getInstance();


    public R2RUpwardExtension(Map<String, List<String>> schema) {
        extension = new UpwardExtension(schema);

    }
    public R2RUpwardExtension(UpwardExtension extension) {
        this.extension = extension;

    }
    @Override
    public Stream<Graph> eval(Stream<Graph> tvg) {
        return tvg.map(
            g -> g.stream()
                  .map(triple -> performUpwardExtension(triple))
                  .flatMap(Collection::stream)
                  .collect(TripleCollector.toGraph())
            );
    }
    private List<Triple> performUpwardExtension(Triple t){
        if(isTypeAssertion(t)){
            String type = t.getObject().ntriplesString();
            type = RDFUtils.trimTags(type);
            //reasoning step
            List<Triple> upward = extension.getUpwardExtension(type).stream()
                    .map(parents -> instance.createTriple(t.getSubject(), RDFTYPE, instance.createIRI(parents)))
                    .collect(Collectors.toList());
            upward.add(t);
            return upward;
        }else{
            return Collections.singletonList(t);
        }
    }


    private boolean isTypeAssertion(Triple t) {
        return t.getPredicate().equals(instance.createIRI("a")) || t.getPredicate().equals(RDFTYPE);
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
