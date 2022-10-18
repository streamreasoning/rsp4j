package org.streamreasoning.rsp4j.wspbook.wildstreams;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.jena.graph.Graph;
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
import org.streamreasoning.rsp4j.io.sources.SSESource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.mapping.CARMLJSONMapper;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/***
 * This example will show you how to process live data from Wikimedia streams.
 */
public class ProcessingWikimedia {

    public static void main(String[] args) throws IOException, ConfigurationException {
        // First a new stream is opened that fetches the data from wikimedia
        SSESource<String> sseSource = new SSESource("https://stream.wikimedia.org/v2/stream/recentchange",1000);
        sseSource.addRequestOptions("Accept","application/json");
        sseSource.stream();
        // Next the RML mapping is defined that converts the raw JSON data to RDF triples
        String rmlMapping =
        ""
            + "@prefix : <http://vocab.org/transit/terms/>.\n"
            + "@prefix rr: <http://www.w3.org/ns/r2rml#>.\n"
            + "@prefix rml: <http://semweb.mmlab.be/ns/rml#>.\n"
            + "@prefix ql: <http://semweb.mmlab.be/ns/ql#>.\n"
            + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n"
            + "@prefix wgs84_pos: <http://www.w3.org/2003/01/geo/wgs84_pos#>.\n"
            + "@prefix carml: <http://carml.taxonic.com/carml/> .\n"
            + "@prefix wiki: <http://vocab.org/transit/terms/>.\n"
            + "\n"
            + "\n"
            + "<#WikimediaMapping> a rr:TriplesMap ;\n"
            + "  rml:logicalSource [\n"
            + "    rml:source [\n"
            + "      a carml:Stream ;\n"
            + "             carml:streamName \"https://stream.wikimedia.org/v2/stream/recentchange\" ;\n"
            + "\n"
            + "    ] ;\n"
            + "    rml:referenceFormulation ql:JSONPath ;\n"
            + "    rml:iterator \"$\" ;\n"
            + "  ] ;\n"
            + "\n"
            + "  rr:subjectMap [\n"
            + "    rr:template \"http://www.wikimedia.com/{id}\" ;\n"
            + "    rr:graphMap [ rr:template  \"http://wiki.time.com/{timestamp}\" ]\n"
            + "\n"
            + "  ] ;\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "        rr:predicate wiki:url ;\n"
            + "        rr:objectMap [\n"
            + "          rr:template \"{server_url}/wiki/{title}\" ;\n"
            + "        ] ;\n"
            + "      ] ;\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "    rr:predicate wiki:title ;\n"
            + "    rr:objectMap [\n"
            + "      rr:template \"https://www.wikidata.org/wiki/{title}\" ;\n"
            + "    ] ;\n"
            + "  ] ;\n"
            + "\n"
            + "\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "    rr:predicate wiki:comment ;\n"
            + "    rr:objectMap [\n"
            + "      rml:reference \"comment\" ;\n"
            + "    ] ;\n"
            + "  ] ;\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "    rr:predicate wiki:wiki ;\n"
            + "    rr:objectMap [\n"
            + "      rml:reference \"wiki\" ;\n"
            + "    ] ;\n"
            + "  ] ;\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "    rr:predicate wiki:server_url ;\n"
            + "    rr:objectMap [\n"
            + "      rml:reference \"server_url\" ;\n"
            + "    ] ;\n"
            + "  ] ;\n"
            + "\n"
            + "  rr:predicateObjectMap [\n"
            + "    rr:predicate wiki:user ;\n"
            + "    rr:objectMap [\n"
            + "      rml:reference \"user\" ;\n"
            + "    ] ;\n"
            + "  ] ;\n"
            + ".";
        // Create an RML mapper
        // Note that the URL of the stream is in the RML mapping
        CARMLJSONMapper mapper = new CARMLJSONMapper(rmlMapping,"https://stream.wikimedia.org/v2/stream/recentchange");
        // Map the Stream with CSV Strings to NT Strings using the RML Mapper
        // We need to to define the URL of the result stream
        DataStreamImpl<String> mappedStream = sseSource.map(mapper::apply, "http://example.org/test/mapped");
        // We map the RDF stream to a stream of internal graph objects
        DataStreamImpl<org.apache.jena.graph.Graph> rdfStream =  mappedStream.map(e->parse(e), "http://example.org/test/rdf");

        mappedStream.addConsumer((e, t) -> System.out.println(e));
        // we define the RSP-QL query to process the data stream
        String rspqlQuery =
                "PREFIX gdelt: <http://gdelt.org/vocab/>\n"
                        + "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n"
                        + "PREFIX :   <https://www.geldt.org/stream#>\n"
                        + "REGISTER RSTREAM <http://out/stream> AS\n"

                        + "Select ?wiki ?count "
                        + "FROM NAMED WINDOW <w> ON <http://example.org/test/rdf> [RANGE PT60S STEP PT10S]\t\n"
                        + " WHERE { {"
                        + "SELECT ?wiki (COUNT(?event) AS ?count) \n"
                        + "WHERE{\n"
                        + " WINDOW <w> {   ?event <http://vocab.org/transit/terms/wiki> ?wiki"
                        + "    }  \n"
                        + "} GROUP BY ?wiki} " +
                        " FILTER(?count >2) } Order By DESC(?count) ";
        // We define the CSPARQL 2.0 engine
        URL resource = ProcessingGDELT.class.getResource("/csparql.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");
        CSPARQLEngine sr = new CSPARQLEngine(0, ec);

        DataStream<Graph> registered = sr.register(rdfStream);
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
        return dataModel.getGraph();
    }
}
