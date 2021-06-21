package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.RDFManager;


public class RDF {

    private static final String uri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

    public static final IRI pTYPE = resource("type");


}
