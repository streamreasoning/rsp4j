package org.streamreasoning.rsp4j.api;

import org.apache.commons.rdf.api.*;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class RDFUtils {

    private static final RDF rdf;

    static {
        rdf = new SimpleRDF();
    }

    public static RDF getInstance() {
        ServiceLoader<RDF> loader = ServiceLoader.load(RDF.class);
        Iterator<RDF> iterator = loader.iterator();
        RDF rdf = iterator.next();
        if (rdf == null) {
            rdf = new SimpleRDF();
        }
        return rdf;
    }

    public static Graph createGraph() {
        return getInstance().createGraph();
    }

    public static Graph createGraph(BlankNodeOrIRI name, List<Quad> quadList) {
        Graph graph = getInstance().createGraph();
        for (Quad quad : quadList) {
            graph.add(quad.asTriple());
        }
        return graph;
    }

    public static IRI createIRI(String w1) {
        return getInstance().createIRI(w1);
    }

    public static Quad createQuad(BlankNodeOrIRI newGraphName, BlankNodeOrIRI newSubject, IRI newPredicate, RDFTerm newObject) {
        return getInstance().createQuad(newGraphName, newSubject, newPredicate, newObject);
    }

    public static RDFTerm createBlankNode(String s) {
        return getInstance().createBlankNode(s);
    }

    public static RDFTerm createLiteral(String lexicalForm, IRI s) {
        return getInstance().createLiteral(lexicalForm, s);
    }

    public static RDFTerm createLiteral(String lexicalForm, String s) {
        return getInstance().createLiteral(lexicalForm, s);
    }

    public static String trimTags(String s) {
        return s.replaceAll("^<(.*)>$", "$1");
    }
    public static String trimQuotes(String s) {
        return s.replaceAll("^['\"](.*)['\"]$", "$1");
    }

    public static String trimFirst(String s) {
        return s.replaceAll("^.(.*)$", "$1");
    }

    public static String trimLast(String s) {
        return s.replaceAll("^(.*).$", "$1");
    }



}
