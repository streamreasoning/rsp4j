package org.streamreasoning.rsp;

import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;

import java.util.ServiceLoader;

public class RDFManager {

    static {
        ServiceLoader<RDF> loader = ServiceLoader.load(RDF.class);
        RDF = loader.iterator().next();
    }

    public static RDF RDF;

    public static IRI resource(String uri, String local) {
        return RDF.createIRI(uri + local);
    }

}
