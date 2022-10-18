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
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.wspbook.wildstreams.dbplutils.DBPLDataFetcher;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

/***
 * This example shows how to analyse data from DBPedia Live.
 * It will download the reinserted triples from DBPedia Live and analyse them in a streaming fashion.
 */
public class ProcessingDBPediaLive {

    public static void main(String[] args) throws ConfigurationException {
        // Creates a DataStream that fetches the updates from DBPedia Live
        DBPLDataFetcher dataFetcher = new DBPLDataFetcher(DBPLDataFetcher.BDPLStreamType.REINSTERT, 3000,2019);
        DataStreamImpl<Graph> dbplStream = dataFetcher.getStream();
        // Defines the RSP-QL query that counts all the different types of updates in the stream.
        String rspqlQuery =
                "PREFIX : <http://rsp4j.io/covid/> "
                                + " PREFIX rsp4j: <http://rsp4j.io/> "
                                + "REGISTER RSTREAM <http://out/stream> AS "
                                + "SELECT ?type (COUNT(?s) AS ?count) "
                                + " "
                                + " FROM NAMED WINDOW rsp4j:window ON <https://live.dbpedia.org/live/sync/changes> [RANGE PT1S STEP PT1S] "

                                + "WHERE {"
                                + "   WINDOW rsp4j:window { ?s a ?type .}"

                                + "} GROUP BY ?type";
        // Configures the C-SPARQL 2.0 engine
        URL resource = ProcessingGDELT.class.getResource("/csparql.properties");
        SDSConfiguration config = new SDSConfiguration(resource.getPath());
        EngineConfiguration ec = EngineConfiguration.loadConfig("/csparql.properties");
        CSPARQLEngine sr = new CSPARQLEngine(0, ec);

        DataStream<org.apache.jena.graph.Graph> registered = sr.register(dbplStream);
        dbplStream.addConsumer((e,t)->registered.put(e,t));

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
        dataFetcher.start();

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
