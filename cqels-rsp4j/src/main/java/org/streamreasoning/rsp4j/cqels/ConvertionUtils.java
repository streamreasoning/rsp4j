package org.streamreasoning.rsp4j.cqels;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;

public class ConvertionUtils {

    public static Triple commonsToJena(org.apache.commons.rdf.api.Triple triple){
        return Triple.create(
                Node.createURI(RDFUtils.trimTags(triple.getSubject().toString())),
                Node.createURI(RDFUtils.trimTags(triple.getPredicate().toString())),
                Node.createURI(RDFUtils.trimTags(triple.getObject().toString())));
    }

    public static org.apache.commons.rdf.api.Triple jenaToCommons(Triple triple){
        return RDFUtils.createTriple(RDFUtils.createIRI(triple.getSubject().getURI()),
                RDFUtils.createIRI(triple.getPredicate().getURI()),
                RDFUtils.createIRI(triple.getObject().getURI()));
    }
}
