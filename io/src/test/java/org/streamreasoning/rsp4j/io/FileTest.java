package org.streamreasoning.rsp4j.io;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;
import org.streamreasoning.rsp4j.io.sinks.FileSink;
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.BufferedConsumer;
import org.streamreasoning.rsp4j.io.utils.ParsingStrategyTest;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFParsingStrategy;
import org.streamreasoning.rsp4j.io.utils.serialization.JenaRDFSerializationStrategy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;

import static org.junit.Assert.assertEquals;

public class FileTest {
    public static Graph createGraph(int index) {
        RDF rdf = new SimpleRDF();

        Graph graph = rdf.createGraph();
        IRI subject = rdf.createIRI("http://test/subject" + index);
        IRI property = rdf.createIRI("http://test/property" + index);
        IRI object = rdf.createIRI("http://test/object" + index);
        graph.add(subject, property, object);
        return graph;
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

    //@Test
    public void testFileSource() throws InterruptedException {
        // define lines of file
        String line1 = "<http://test/subject1> <http://test/property1> <http://test/object1>.";
        String line2 = "<http://test/subject2> <http://test/property2> <http://test/object2>.";
        String[] inputLines = new String[]{line1, line2};

        String filePath = "filetest_" + System.currentTimeMillis() + ".txt";

        Path path = createFile(inputLines, filePath);
        // create parsing strategy
        JenaRDFParsingStrategy parsingStrategy = new JenaRDFParsingStrategy(RDFBase.NT);
        // create file source to read the newly created file
        FileSource<Graph> fileSource = new FileSource<Graph>(filePath, 0, parsingStrategy);
        // create and add dummy consumer
        BufferedConsumer<Graph> bufferedConsumer = new BufferedConsumer<>();
        fileSource.addConsumer(bufferedConsumer);
        // start reading from file
        fileSource.stream();
        // sleep to count for IO overhead
        Thread.sleep(100);
        // check dummy consumer size
        assertEquals(bufferedConsumer.getSize(), 2);
        // create expected graphs
        Graph expectedGraph1 = createGraph(1);
        Graph expectedGraph2 = createGraph(2);
        //compare graph content
        ParsingStrategyTest.compareGraph(expectedGraph1, bufferedConsumer.getMessage(0));
        ParsingStrategyTest.compareGraph(expectedGraph2, bufferedConsumer.getMessage(1));
        // delete file
        deleteFile(path);
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

    //  @Test
    public void fileSinkTest() {
        // define expected lines of file
        String line1 = "<http://test/subject1> <http://test/property1> <http://test/object1> .";
        String line2 = "<http://test/subject2> <http://test/property2> <http://test/object2> .";
        String[] inputLines = new String[]{line1, line2};
        // create  the input graphs
        Graph graph1 = createGraph(1);
        Graph graph2 = createGraph(2);
        // create serialization strategy
        JenaRDFSerializationStrategy serializationStrategy = new JenaRDFSerializationStrategy(RDFBase.NT);
        String filePath = "filetest_" + System.currentTimeMillis() + ".txt";
        // create file sink
        FileSink<Graph> fileSink = new FileSink<Graph>(filePath, serializationStrategy);

        fileSink.put(graph1, System.currentTimeMillis());
        fileSink.put(graph2, System.currentTimeMillis());
        // read the file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineIndex = 0;
            while ((line = br.readLine()) != null) {
                // compare file content with expected liens
                assertEquals(inputLines[lineIndex], line);
                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // delete test file
        deleteFile(Paths.get(filePath));
    }
}
