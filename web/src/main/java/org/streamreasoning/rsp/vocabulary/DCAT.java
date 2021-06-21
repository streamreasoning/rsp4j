package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp.RDFManager;


public class DCAT {

    private static final String uri = "http://www.w3.org/ns/dcat#";


    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

    public static final IRI pNAME = resource("name");
    public static final IRI pTITLE = resource("title");
    public static final IRI pPUBLISHER = resource("publisher");
    public static final IRI pDESCRIPTION = resource("description");
    public static final IRI pLICENSE = resource("license");


}
