package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.RDFManager;


public class XSD {

    private static final String uri = "http://www.w3.org/2001/XMLSchema#";

    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

    public static final IRI tString = resource("string");


}
