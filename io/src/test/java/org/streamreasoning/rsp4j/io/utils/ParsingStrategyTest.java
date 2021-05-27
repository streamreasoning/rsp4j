package org.streamreasoning.rsp4j.io.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingResult;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.parsing.TimeExtractingParsingStrategy;


import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ParsingStrategyTest {
    public static Graph createGraph(){
        RDF rdf = new SimpleRDF();

        Graph graph = rdf.createGraph();
        IRI subject = rdf.createIRI("http://test/subject");
        IRI property = rdf.createIRI("http://test/property");
        IRI object = rdf.createIRI("http://test/object");
        graph.add(subject, property, object);
        return graph;
    }
    public static void compareGraph(Graph g1, Graph g2){
        //Small hack as equal method in JenaRDF was not correctly overwritten
        Set<? extends Triple> triples = g1.stream().collect(Collectors.toSet());
        Set<? extends Triple> parsedTriples = g2.stream().collect(Collectors.toSet());
        assertEquals(triples,parsedTriples);
    }
    public static void parseAndCompare(String message, RDFBase base){
        ParsingStrategy<Graph> jenaRDFParser = new JenaRDFParsingStrategy(base);
        ParsingResult<Graph> parsedResult = jenaRDFParser.parse(message);
        Graph parsedGraph = parsedResult.getResult();
        Graph graph = createGraph();
        compareGraph(graph,parsedGraph);
    }
    @Test
    public void JenaRDFTTLParsingTest(){
        RDFBase base = RDFBase.TTL;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        parseAndCompare(message,base);
    }
    @Test
    public void JenaRDFN3ParsingTest(){
        RDFBase base = RDFBase.N3;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        parseAndCompare(message,base);
    }
    @Test
    public void JenaRDFNTParsingTest(){
        RDFBase base = RDFBase.NT;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        parseAndCompare(message,base);
    }
    @Test
    public void JenaRDFRDFXMLParsingTest(){
        RDFBase base = RDFBase.RDFXML;
        String message =
                    "<?xml version=\"1.0\"?>\n"
                        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                        + "xml:base=\"http://test/\"\n"
                        + "xmlns:test=\"http://test/\""
                        + ">\n"
                        + "\n"
                        + "<rdf:Description rdf:about=\"http://test/subject\">\n"
                        + "  <test:property rdf:resource=\"http://test/object\" />\n"
                        + "</rdf:Description>\n"
                        + "\n"
                        + "</rdf:RDF>\n";
        parseAndCompare(message,base);
    }
    @Test
    public void JenaRDFJSONLDParsingTest(){
        RDFBase base = RDFBase.JSONLD;
        String message =
                    "{\n"
                        + "  \"@context\": {\n"
                        + "    \"gr\": \"http://test/\",\n"
                        + "     \"gr:property\": {\n"
                        + "      \"@type\": \"@id\"\n"
                        + "    }\n"
                        + "  \n"
                        + "  },  \n"
                        + "  \"@id\": \"http://test/subject\",  \t\n"
                        + "  \"gr:property\": \"gr:object\"\n"
                        + "  \n"
                        + "}";
        parseAndCompare(message,base);
    }
    @Test
    public void timeExtractionParsingTest(){
        RDFBase base = RDFBase.NT;
        // first test with time at index 0
        String message = "12345, <http://test/subject> <http://test/property> <http://test/object>.";
        ParsingStrategy<Graph> jenaRDFParser = new JenaRDFParsingStrategy(base);
        TimeExtractingParsingStrategy<Graph> timeExtractor = new TimeExtractingParsingStrategy<>(0,",",jenaRDFParser);
        ParsingResult<Graph> parsedResult = timeExtractor.parse(message);
        Graph parsedGraph = parsedResult.getResult();
        Graph graph = createGraph();
        compareGraph(graph,parsedGraph);
        assertEquals(12345l,parsedResult.getTimeStamp());
        // now check with time at index 1
        message = "<http://test/subject> <http://test/property> <http://test/object>., 12345";
        timeExtractor = new TimeExtractingParsingStrategy<>(1,",",jenaRDFParser);
        parsedResult = timeExtractor.parse(message);
        parsedGraph = parsedResult.getResult();
        compareGraph(graph,parsedGraph);
        assertEquals(12345l,parsedResult.getTimeStamp());
    }

}
