package org.streamreasoning.rsp4j.wspbook.wildstreams;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.rdf.api.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.streamreasoning.rsp4j.api.engine.config.EngineConfiguration;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDSConfiguration;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.csparql2.engine.CSPARQLEngine;
import org.streamreasoning.rsp4j.csparql2.engine.JenaContinuousQueryExecution;
import org.streamreasoning.rsp4j.csparql2.sysout.ResponseFormatterFactory;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.mapping.CARMLCSVMapper;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.wspbook.wildstreams.gdeltutils.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/***
 * This example shows how to process the live data from the GDELT project.
 */
public class ProcessingGDELT {
    private static String prefix = "http://gdelt.org/gkg/";
    private static String semicolon = ";";
    // Defines some additional functions to enrich the raw CSV data
    private static Object[] functions = new Object[]{new DBPediaPeopleLookup(),
            new DBPediaPeopleLookup("http://xmlns.com/foaf/0.1/Person,Wikidata:Q5,Wikidata:Q24229398,Wikidata:Q215627,DUL:NaturalPerson,DUL:Agent,Schema:Person,DBpedia:Person,DBpedia:Agent".split(",")),
            new URISplitFunction(semicolon),
            new RegexSplitterFunction("(.*)<PAGE_PRECISEPUBTIMESTAMP>([0-9]+)</PAGE_PRECISEPUBTIMESTAMP>(.*)", 2),
            new GenericSplitFunction(semicolon, prefix),
            new ThemeV2SplitFunction(semicolon, prefix)};


    public static void main(String[] args) throws IOException, URISyntaxException, ConfigurationException {
        // First a stream is created that fetches the GDELT stream
        DataStreamImpl<String> gdeltStream = new DataStreamImpl<>("GDELTStream");
        GDELTDataFetcher fetcher = new GDELTDataFetcher("export", gdeltStream, 1000);
        // A mapping is define the map the raw CSV files to RDF
        String rmlMapping = Files.readString(Path.of(ProcessingGDELT.class.getResource("/mapping/gdelt_export.ttl").toURI()));
        CARMLCSVMapper mapper = new CARMLCSVMapper(rmlMapping,"GDELTStream", functions);
        // The raw data is converted to RDF in string format and then to internal RDF graph objects
        DataStreamImpl<String> mappedStream = gdeltStream.map(mapper::apply, "http://example.org/test/mapped");
        JenaRDFCommonsParsingStrategy jenaParser = new JenaRDFCommonsParsingStrategy(RDFBase.NT);
        DataStreamImpl<org.apache.jena.graph.Graph> rdfStream =  mappedStream.map(e->parse(e), "http://example.org/test/rdf");
        // Defines the RSPQL query that processes the GDELT data
        String rspqlQuery =
        "PREFIX gdelt: <http://gdelt.org/vocab/>\n"
            + "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n"
            + "PREFIX :   <https://www.geldt.org/stream#>\n"
            + "REGISTER RSTREAM <http://out/stream> AS\n"
            + "SELECT *\n"
            + "FROM NAMED WINDOW <w> ON <http://example.org/test/rdf> [RANGE PT1S STEP PT1S]\t\n"
            + "WHERE{\n"
            + " WINDOW <w> {   ?event  gdelt:actor1 ?actor1.\n"
            + "    ?actor1 gdelt:actorName \"CHINA\"^^xsd:string \n"
            + "    }\n"
            + "}";
        // Configures the CSPARQL2.0 engine
        URL resource = ProcessingGDELT.class.getResource("/csparql.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");
        CSPARQLEngine sr = new CSPARQLEngine(0, ec);

        DataStream<org.apache.jena.graph.Graph> registered = sr.register(rdfStream);
        rdfStream.addConsumer((e,t)->registered.put(e,t));

        JenaContinuousQueryExecution cqe = (JenaContinuousQueryExecution)sr.register(rspqlQuery, config);

        ContinuousQuery query = cqe.query();

        System.out.println(query.toString());

        System.out.println("<<------>>");

        if (query.isConstructType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getConstructResponseSysOutFormatter("JSON-LD", true));
        } else if (query.isSelectType()) {
            cqe.addQueryFormatter(ResponseFormatterFactory.getSelectResponseSysOutFormatter("TABLE", true)); //or "CSV" or "JSON" or "JSON-LD"
        }

        DataStream outputStream = cqe.outstream();
        outputStream.addConsumer((o, l) -> System.out.println(o));
        fetcher.start();

    }
    public static org.apache.jena.graph.Graph parse(String parseString) {
        Model dataModel = ModelFactory.createDefaultModel();
        try {
            InputStream targetStream = new ByteArrayInputStream(parseString.getBytes());
            dataModel.read(targetStream, null, RDFBase.NT.name());
            return dataModel.getGraph();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
