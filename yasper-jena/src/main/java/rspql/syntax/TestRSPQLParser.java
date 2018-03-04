package rspql.syntax;

import java.io.IOException;

public class TestRSPQLParser {

    public static void main(String[] args) throws IOException {
        String trickyQuery = "" +
                "PREFIX : <ex#> " +
                "REGISTER ISTREAM :test AS " +
                "CONSTRUCT { " +
                "  ?a ?b ?c " +
                "} " +
                "FROM NAMED WINDOW <ex#w1> ON <ex#s> [ RANGE PT1H ] " +
                "FROM NAMED WINDOW ex:w2 ON ex:s [ ELEMENTS 10 STEP 10 ] " +
                "FROM NAMED WINDOW ex:w3 ON ex:s [ RANGE PT1H STEP PT1H ] " +
                "FROM NAMED WINDOW ex:w4 ON ex:s [ ELEMENTS 5 ] " +
                "WHERE {" +
                "   WINDOW :w { " +
                "      ?s ?p ?c " +
                "   } " +
                "   WINDOW ?w { " +
                "      ?s ?p ?c " +
                "   } " +
                "   GRAPH ?g { " +
                "      ?s ?p ?c " +
                "   } " +
                "}";

        RSPQLJenaQuery query = QueryFactory.parse(trickyQuery);

        // Print the query (only the SPARQL 1.1 parts)
        System.out.println(query);

        System.out.println("----------");
        // Print the RSP-QL specific parts
        System.out.printf("REGISTER %s <%s> AS \n", query.getStreamType(),  query.getOutputStreamUri());
        System.out.println("...");
        for(NamedWindow window : query.getNamedWindows()){
            System.out.println(window);
        }
        for(ElementNamedWindow elementWindow : query.getElementNamedWindows()){
            String n = elementWindow.getWindowNameNode().toString();
            System.out.println("WINDOW <" + n + " > {");
            System.out.println(elementWindow.getElement());
            System.out.println("}");
            System.out.println();
        }



    }
}
