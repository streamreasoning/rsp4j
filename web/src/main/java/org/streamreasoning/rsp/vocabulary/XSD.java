package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;


public class XSD extends Vocabulary {

    private static final String uri = "http://www.w3.org/2001/XMLSchema#";
    public static final IRI tString = resource("string");

    private static IRI resource(String rdfStream) {
        return Vocabulary.resource(uri, rdfStream);
    }


}
