package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp.RDFManager;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;
import org.streamreasoning.rsp4j.api.RDFUtils;


public class DCAT {

    private static final String uri = "http://www.w3.org/ns/dcat#";
    public static final IRI pNAME = resource("name");
    public static final IRI pTITLE = resource("title");
    public static final IRI pPUBLISHER = resource("publisher");
    public static final IRI pDESCRIPTION = resource("description");
    public static final IRI pLICENSE = resource("license");
    public static final IRI pFORMAT = resource("format");
    public static final IRI pDATASET = resource("dataset");
    public static IRI pACCESS = resource("access");
    public static IRI pSECUTIRTY = resource("security");
    public static IRI pPROTOCOL = resource("protocol");

    private static IRI resource(String rdfStream) {
        return RDFManager.resource(uri, rdfStream);
    }

    public static Triple format(BlankNodeOrIRI s, Format o) {
        return RDFUtils.getInstance().createTriple(s, pFORMAT, RDFUtils.createLiteral(o.name(), XSD.tString));
    }

    public static Triple name(BlankNodeOrIRI s, String name) {
        return RDFUtils.getInstance().createTriple(s, pNAME, RDFUtils.createLiteral(name, XSD.tString));
    }

    public static Triple description(BlankNodeOrIRI uri, String description) {
        return RDFUtils.getInstance().createTriple(uri, pDESCRIPTION, RDFUtils.createLiteral(description, XSD.tString));
    }

    public static Triple dataset(BlankNodeOrIRI s, BlankNodeOrIRI o) {
        return RDFUtils.getInstance().createTriple(s, pDATASET, o);
    }

    public static Triple license(BlankNodeOrIRI s, License l) {
        return RDFUtils.getInstance().createTriple(s, pLICENSE, l.url());
    }

    public static Triple access(BlankNodeOrIRI uri, String s) {
        return RDFUtils.getInstance().createTriple(uri, pACCESS, RDFUtils.createLiteral(s, XSD.tString));

    }
}
