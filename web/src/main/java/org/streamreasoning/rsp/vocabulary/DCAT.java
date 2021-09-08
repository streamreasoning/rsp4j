package org.streamreasoning.rsp.vocabulary;

import org.apache.commons.rdf.api.BlankNodeOrIRI;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp.enums.Format;
import org.streamreasoning.rsp.enums.License;


public class DCAT extends Vocabulary {

    private static final String uri = "http://www.w3.org/ns/dcat#";
    public static final IRI pNAME = resource("name");
    public static final IRI pPUBLISHER = resource("publisher");
    public static final IRI pDESCRIPTION = resource("description");
    public static final IRI pLICENSE = resource("license");
    public static final IRI pFORMAT = resource("format");
    public static final IRI pDATASET = resource("dataset");
    public static IRI pACCESS = resource("access");
    public static IRI pSECUTIRTY = resource("security");
    public static IRI pPROTOCOL = resource("protocol");

    private static IRI resource(String rdfStream) {
        return Vocabulary.resource(uri, rdfStream);
    }

    public static Triple format(BlankNodeOrIRI s, Format o) {
        return triple(s, pFORMAT, is.createLiteral(o.name(), XSD.tString));
    }

    public static Triple name(BlankNodeOrIRI s, String name) {
        return triple(s, pNAME, is.createLiteral(name, XSD.tString));
    }

    public static Triple publisher(BlankNodeOrIRI s, BlankNodeOrIRI o) {
        return triple(s, pPUBLISHER, o);
    }

    public static Triple description(BlankNodeOrIRI s, String description) {
        return triple(s, pDESCRIPTION, is.createLiteral(description, XSD.tString));
    }

    public static Triple dataset(BlankNodeOrIRI s, BlankNodeOrIRI o) {
        return triple(s, pDATASET, o);
    }

    public static Triple license(BlankNodeOrIRI s, License l) {
        return triple(s, pLICENSE, l.url());
    }

    public static Triple access(BlankNodeOrIRI uri, String s) {
        return triple(uri, pACCESS, is.createLiteral(s, XSD.tString));

    }
}
