package org.streamreasoning.rsp4j.mapping;

import org.junit.Assert;
import org.junit.Test;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.sources.FileSource;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CSVStreamTest {
    // Raw CSV file
    String inputCSV="Year;Make;Model;Description;Price\n" +
            "1997;Ford;E350;\"ac, abs, moon\";3000,00\n" +
            "1999;Chevy;\"Venture \"\"Extended Edition\"\"\";\"\";4900,00\n" +
            "1999;Chevy;\"Venture \"\"Extended Edition, Very Large\"\"\";;5000,00\n" +
            "1996;Jeep;Grand Cherokee;\"MUST SELL!\n" +
            "air, moon roof, loaded\";4799,00";
    // Smaller CSV file
    String inputCSVSmall="Year;Make;Model;Description;Price\n" +
            "1997;Ford;E350;\"ac, abs, moon\";3000,00";
    String rmlMapping =
      "@prefix rr: <http://www.w3.org/ns/r2rml#> .\n"
          + "@prefix rml: <http://semweb.mmlab.be/ns/rml#> .\n"
          + "@prefix ql: <http://semweb.mmlab.be/ns/ql#> .\n"
          + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
          + "@prefix exm: <http://example.com/mapping/> .\n"
          + "@prefix data: <http://example.com/data/> .\n"
          + "@prefix car: <http://example.com/car/> .\n"
          + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
          + "@prefix dct: <http://purl.org/dc/terms/> .\n"
          + "@prefix carml: <http://carml.taxonic.com/carml/> .\n"

                                  + "\n"
          + "exm:LogicalSource a rml:LogicalSource ;\n" +
            "\t\trml:source [\n" +
                    "\t\t\ta carml:Stream;\n" +
                    "\t\t\tcarml:streamName \"http://example.org/test\";\n" + // <- Stream name is in the  RML Mapping!
                    "\t\t];\n"
           + "  rml:referenceFormulation ql:CSV ;\n"
          + ".\n"
          + "\n"
          + "exm:CarMapping a rr:TriplesMap ;\n"
          + "  rml:logicalSource exm:LogicalSource ;\n"
          + "  rr:subjectMap [\n"
          + "    rr:template \"http://example.com/data/car/{Make}-{Model}\" ;\n"
          + "    rr:class car:Car ;\n"
          + "  ] ;\n"
          + "  rr:predicateObjectMap [\n"
          + "    rr:predicate car:make ;\n"
          + "    rr:objectMap [\n"
          + "      rr:parentTriplesMap exm:MakeMapping ;\n"
          + "    ] ;\n"
          + "  ] ;\n"
          + "  rr:predicateObjectMap [\n"
          + "    rr:predicate dct:description ;\n"
          + "    rr:objectMap [\n"
          + "      rml:reference \"Description\" ;\n"
          + "    ] ;\n"
          + "  ] ;\n"
          + "  rr:predicateObjectMap [\n"
          + "    rr:predicate car:year ;\n"
          + "    rr:objectMap [\n"
          + "      rr:template \"http://example.com/data/year/{Year}\" ;\n"
          + "    ] ;\n"
          + "  ] ;\n"
          + "  rr:predicateObjectMap [\n"
          + "    rr:predicate car:price ;\n"
          + "    rr:objectMap [\n"
          + "      rml:reference \"Price\" ;\n"
          + "    ] ;\n"
          + "  ] ;\n"
          + ".\n"
          + "\n"
          + "exm:MakeMapping a rr:TriplesMap ;\n"
          + "  rml:logicalSource exm:LogicalSource ;\n"
          + "  rr:subjectMap [\n"
          + "    rr:template \"http://example.com/data/make/{Make}\" ;\n"
          + "    rr:class car:CarMake ;\n"
          + "  ] ;\n"
          + ".";

    @Test
    public void singleEventMappingTest(){
        // Define the stream which will be populated
        DataStreamImpl<String> stream = new DataStreamImpl<>("http://example.org/test");
        // Create an RML mapper
        // Note that the URL of the stream is in the RML mapping
        CARMLCSVMapper mapper = new CARMLCSVMapper(rmlMapping,"http://example.org/test");
        // Map the Stream with CSV Strings to NT Strings using the RML Mapper
        // We need to to define the URL of the result stream
        DataStreamImpl<String> rdfStream = stream.map(mapper::apply, "http://example.org/test/mapped");

        // add a consumer to see the results
        rdfStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
        List<String> results = new ArrayList<>();
        rdfStream.addConsumer((el, ts)->results.add(el));

        // add a small CSV snippet to the stream
        stream.put(inputCSVSmall, 0l);

        String expected =
                "<http://example.com/data/car/Ford-E350> <http://example.com/car/make> <http://example.com/data/make/Ford> .\n"
                        + "<http://example.com/data/car/Ford-E350> <http://example.com/car/price> \"3000,00\" .\n"
                        + "<http://example.com/data/car/Ford-E350> <http://example.com/car/year> <http://example.com/data/year/1997> .\n"
                        + "<http://example.com/data/car/Ford-E350> <http://purl.org/dc/terms/description> \"ac, abs, moon\" .\n"
                        + "<http://example.com/data/car/Ford-E350> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.com/car/Car> .\n"
                        + "<http://example.com/data/make/Ford> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://example.com/car/CarMake> .";
        // Check if the csv has been converted
        Assert.assertEquals(expected, results.get(0));
    }
    @Test
    public void fileStreamTest() throws InterruptedException {
        // define lines of file
        String[] inputLines = inputCSV.split("\n");
        String filePath = "filetest_" + System.currentTimeMillis() + ".txt";
        // create a temp. file
        Path path = createFile(inputLines, filePath);


        // create file source to read the newly created file
        // The file will be read one line at a time
        FileSource<String> fileSource = new FileSource<>(filePath, "http://example.org/test",0);
        // Map to RDF
        CARMLCSVMapper mapper = new CARMLCSVMapper(rmlMapping,"http://example.org/test");
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
        return String.format(inputCSV, index +"");
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
