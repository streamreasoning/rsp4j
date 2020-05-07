package it.polimi.sr.rsp.csparql.syntax;

import it.polimi.jasper.CSPARQLReadyToGo.CSPARQLReadyToGo;
import it.polimi.sr.rsp.csparql.engine.CSPARQLEngine;
import it.polimi.yasper.core.engine.config.EngineConfiguration;
import it.polimi.yasper.core.sds.SDSConfiguration;
import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;

public class TestRSPQLParser {

    public static void main(String[] args) throws IOException, ConfigurationException {

        String path = CSPARQLReadyToGo.class.getResource("/csparql.properties").getPath();
        SDSConfiguration config = new SDSConfiguration(path);
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");


        CSPARQLEngine sr = new CSPARQLEngine(0, ec);
        String trickyQuery = "" +
                "PREFIX ars: <http://www.streamreasoning/it.polimi.jasper.test/artist#>\n" +
                "REGISTER RSTREAM <s1> AS\n" +
                "SELECT2 (SUM(?age) AS ?sum) ?a \n" +
                "FROM NAMED WINDOW <win2> ON <stream2> [RANGE PT1M STEP PT1M]\n" +
                "WHERE  {\n" +
                "    WINDOW ?w {\n" +
                "        ?a a ars:Writer ;\n" +
                "           ars:hasAge ?age .\n" +
                "    }\n" +
                "}\n";

        RSPQLJenaQuery query = QueryFactory.parse(ec.getBaseURI(), trickyQuery);
        // Print the query (only the SPARQL 1.1 parts)

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
