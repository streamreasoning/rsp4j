package org.streamreasoning.rsp4j.io.utils;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.serialization.JenaRDFSerializationStrategy;
import org.streamreasoning.rsp4j.io.utils.serialization.StringSerializationStrategy;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class StringSerializationTest {

    public static Graph createGraph(){
        RDF rdf = new SimpleRDF();

        Graph graph = rdf.createGraph();
        IRI subject = rdf.createIRI("http://test/subject");
        IRI property = rdf.createIRI("http://test/property");
        IRI object = rdf.createIRI("http://test/object");
        graph.add(subject, property, object);
        return graph;
    }

    public static void serializeAndCompare(String message, RDFBase base){
        Graph graph = createGraph();
        StringSerializationStrategy<Graph> jenaSerialiazer = new JenaRDFSerializationStrategy(base);
        String serializedGraph = jenaSerialiazer.serialize(graph);
        assertEquals(prepareString(message), prepareString(serializedGraph));

    }
    public static String prepareString(String message){
        return message.replace(" ","")
                .replace(System.lineSeparator(),"")
                .replace("\t","");
    }
    @Test
    public void JenaRDFTTLSerializationTest(){
        RDFBase base = RDFBase.TTL;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        serializeAndCompare(message,base);
    }
    @Test
    public void JenaRDFN3SerializationTest(){
        RDFBase base = RDFBase.N3;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        serializeAndCompare(message,base);
    }
    @Test
    public void JenaRDFNTSerializationTest(){
        RDFBase base = RDFBase.NT;
        String message = "<http://test/subject> <http://test/property> <http://test/object>.";
        serializeAndCompare(message,base);
    }
    @Test
    public void JenaRDFRDFXMLSerializationTest(){
        RDFBase base = RDFBase.RDFXML;
    String message =
        "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"" +
                "xmlns:j.0=\"http://test/\">" +
                "<rdf:Description rdf:about=\"http://test/subject\">" +
                "<j.0:property rdf:resource=\"http://test/object\"/>" +
                "</rdf:Description>" +
                "</rdf:RDF>";
        serializeAndCompare(message,base);
    }
    @Test
    public void JenaRDFJSONLDSerializationTest(){
        RDFBase base = RDFBase.JSONLD;
        String message =
                 "{" +
                "\"@id\":\"http://test/subject\"," +
                "\"property\":\"http://test/object\"," +
                "\"@context\":{" +
                "\"property\":{" +
                "\"@id\":\"http://test/property\"," +
                "\"@type\":\"@id\"}}}";
        serializeAndCompare(message,base);
    }
}
