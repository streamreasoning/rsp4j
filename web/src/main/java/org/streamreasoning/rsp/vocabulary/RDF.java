package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;


public class RDF extends Vocabulary{

    private static final String uri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    public static final IRI pTYPE = resource("type");

    private static IRI resource(String rdfStream) {
        return Vocabulary.resource(uri, rdfStream);
    }


}
