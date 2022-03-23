package org.streamreasoning.rsp4j.mapping;

import org.apache.commons.rdf.api.Graph;
import org.junit.Assert;
import org.junit.Test;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.parsing.ParsingStrategy;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JSONStreamTest {
    // Example Json String that will be mapped to RDF
    String jsonString ="{\n" +
            "\t\"first\"\t: \"Amarise\",\n" +
            "\t\"last\"\t: \"Fernand\",\n" +
            "\t\"loves\"\t: \"milk\",\n" +
            "\t\"birthday\" : \"2017-02-16\",\n" +
            "\t\"language\"\t: \"Nederlands\",\n" +
            "\t\"age\"\t: \"%s\",\n" +
            "\t\"length\"\t: \"5.5\",\n" +
            "\t\"BSN\"\t: \"123456\",\n" +
            "\t\"parents\": \"Gea and Hildebrand\"\n" +
            "}\n";

    String rmlMapping="@prefix rr: <http://www.w3.org/ns/r2rml#>.\n" +
            "@prefix rml: <http://semweb.mmlab.be/ns/rml#>.\n" +
            "@prefix ex: <http://example.com/>.\n" +
            "@prefix ql: <http://semweb.mmlab.be/ns/ql#> .\n" +
            "@prefix carml: <http://carml.taxonic.com/carml/> .\n" +
            "\n" +
            "<#SubjectMapping> a rr:TriplesMap;\n" +
            "\trml:logicalSource [\n" +
            "\t\trml:source [\n" +
            "\t\t\ta carml:Stream;\n" +
            "\t\t\tcarml:streamName \"http://example.org/test\";\n" + // <- Stream name is in the  RML Mapping!
            "\t\t];\n" +
            "\t\trml:referenceFormulation ql:JSONPath;\n" +
            "\t\trml:iterator \"$\"\n" +
            "\t];\n" +
            "\n" +
            "\trr:subjectMap [\n" +
            "\t\trr:template \"http://example.com/Child/{first}/{last}\";\n" +
            "\t\trr:class ex:Child\n" +
            "\t];\n" +
            "\t\n" +
            "\trr:predicateObjectMap [\n" +
            "\t\trr:predicate ex:loves;\n" +
            "\t\trr:objectMap [\n" +
            "\t\t\trml:reference \"loves\";\n" +
            "\t\t]\n" +
            "\t].";

    @Test
    public void singleEventMappingTest(){
        // Define the stream which will be populated
        DataStreamImpl<String> stream = new DataStreamImpl<>("http://example.org/test");
        // Create an RML mapper
        // Note that the URL of the stream is in the RML mapping
        CARMLJSONMapper mapper = new CARMLJSONMapper(rmlMapping,"http://example.org/test");
        // Map the Stream with CSV Strings to NT Strings using the RML Mapper
        // We need to to define the URL of the result stream
        DataStreamImpl<String> rdfStream = stream.map(mapper::apply, "http://example.org/test/mapped");

        // add a consumer to see the results
        rdfStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));

        List<String> results = new ArrayList<>();
        rdfStream.addConsumer((el, ts)->results.add(el));
        // add a json string to the stream
        stream.put(getEvent(0), 0l);

        String expected = "<http://example.com/Child/Amarise/Fernand> <http://example.com/loves> \"milk\" .\n" +
                "<http://example.com/Child/Amarise/Fernand> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.com/Child> .";
        // Check if the json has been converted
        Assert.assertEquals(expected, results.get(0));
    }
    @Test
    public void singleEventMappingWithParsingTest(){
        // Define the stream which will be populated
        DataStreamImpl<String> stream = new DataStreamImpl<>("http://example.org/test");
        // Create an RML mapper
        // Note that the URL of the stream is in the RML mapping
        CARMLJSONMapper mapper = new CARMLJSONMapper(rmlMapping,"http://example.org/test");
        // Map the Stream with CSV Strings to NT Strings using the RML Mapper
        // We need to to define the URL of the result stream
        DataStreamImpl<String> rdfStream = stream.map(mapper::apply, "http://example.org/test/mapped");
        // Here we will map the RDF triple Strings (NTriples format) to Jena Graph objects with a Jena Parser
        ParsingStrategy<Graph> jenaParser = new JenaRDFCommonsParsingStrategy(RDFBase.NT);
        // Mapping of NTriple Strings to Graph objects
        DataStreamImpl<Graph> graphStream = rdfStream.map(jenaParser::parse,"http://example.org/test/parsed");

        // Add consumers
        rdfStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
        graphStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));

        List<String> results = new ArrayList<>();
        rdfStream.addConsumer((el, ts)->results.add(el));

        stream.put(getEvent(0), 0l);

        String expected = "<http://example.com/Child/Amarise/Fernand> <http://example.com/loves> \"milk\" .\n" +
                "<http://example.com/Child/Amarise/Fernand> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.com/Child> .";
        // check the results
        Assert.assertEquals(expected, results.get(0));
    }
    @Test
    public void fileStreamTest() throws InterruptedException {
        // define lines of file
        String line1 = getEvent(0).replace("\n","");
        String line2 = getEvent(1).replace("\n","");
        String[] inputLines = new String[]{line1, line2};

        String filePath = "filetest_" + System.currentTimeMillis() + ".txt";
        // create a temp. file
        Path path = createFile(inputLines, filePath);


        // create file source to read the newly created file
        // The file will be read one line at a time
        FileSource<String> fileSource = new FileSource<>(filePath, "http://example.org/test",0);
        fileSource.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));

        // Map to RDF
        CARMLJSONMapper mapper = new CARMLJSONMapper(rmlMapping,"http://example.org/test");
        DataStreamImpl<String> rdfStream = fileSource.map(mapper::apply, "http://example.org/test/mapped");

        rdfStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
        // start reading from file
        fileSource.stream();
        // sleep to count for IO overhead
        Thread.sleep(1000);

        // delete file
        deleteFile(path);
    }

    private String getEvent(int index){
        return String.format(jsonString, index +"");
    }
    public static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
    public static Path createFile(String[] inputLines, String filePath) {
        Path path = Paths.get(filePath);

        // create file and write lines to file
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String line : inputLines) {
                writer.write(line + System.lineSeparator());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
