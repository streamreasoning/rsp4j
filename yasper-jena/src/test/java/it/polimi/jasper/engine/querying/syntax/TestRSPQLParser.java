package it.polimi.jasper.engine.querying.syntax;

import org.apache.jena.query.Syntax;

import java.io.IOException;

public class TestRSPQLParser {

    public static void main(String[] args) throws IOException {
        String trickyQuery = "" +
                "PREFIX : <ex#> \n" +
                "REGISTER ISTREAM :test AS \n" +
                "CONSTRUCT { \n" +
                "  <s> <p2> <o2> . \n" +
                "  GRAPH <g> { ?s ?p ?o } .\n" +
                "  ?s <p> <o> \n" +
                "} " +
                "FROM NAMED WINDOW <ex#w1> ON <ex#s> [ RANGE PT1H ] \n" +
                "FROM NAMED WINDOW :w2 ON :s [ ELEMENTS 10 STEP 10 ] \n" +
                "FROM NAMED WINDOW :w3 ON :s [ RANGE PT1H STEP PT1H ] \n" +
                "FROM NAMED WINDOW :w4 ON :s [ ELEMENTS 5 ] " +
                "WHERE {\n" +
                "   WINDOW :w1 { \n" +
                "      ?s ?p ?c \n" +
                "   } \n" +
                "   WINDOW ?w { \n" +
                "      ?s ?p ?c \n" +
                "   } \n" +
                "   GRAPH ?g { \n" +
                "      ?s ?p ?c \n" +
                "   } \n" +
                "}";
        System.err.println(trickyQuery);

        RSPQLJenaQuery query = QueryFactory.parse(null, trickyQuery);
        query.setSyntax(Syntax.syntaxARQ);
        // Print the query (only the SPARQL 1.1 parts)
        System.out.println(query);

//        System.out.println("----------");
//        // Print the RSP-QL specific parts
//        System.out.printf("REGISTER %s <%s> AS \n", query.getStreamType(),  query.getOutputStreamUri());
//        System.out.println("...");
//        for(NamedWindow window : query.getNamedWindows()){
//            System.out.println(window);
//        }
//        for(ElementNamedWindow elementWindow : query.getElementNamedWindows()){
//            String n = elementWindow.getWindowNameNode().toString();
//            System.out.println("WINDOW <" + n + " > {");
//            System.out.println(elementWindow.getElement());
//            System.out.println("}");
//            System.out.println();
//        }



    }
}
