package org.streamreasoning.rsp4j.wspbook.wildstreams;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.QueryTaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.HashJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

/***
 * This example shows how to analyse data from DBPedia Live.
 * To simplify analysis, a snippit of the live stream is stored in the file datasets/dbpedialive_snippit.nt.
 */
public class ProcessingDBPediaLive {

    public static void main(String[] args){
        JenaRDFCommonsParsingStrategy parsingStrategy = new JenaRDFCommonsParsingStrategy(RDFBase.NT);
        // create file source to read the newly created file
        String filePath = ProcessingDBPediaLive.class.getResource("/datasets/dbpedialive_snippit.nt").getPath();
        FileSource<Graph> fileSource = new FileSource<Graph>(filePath, 10, parsingStrategy);
        fileSource.stream();

        // Define the query that checks the different types of data in the DBPedia Live Stream
        ContinuousQuery<Graph, Graph, Binding, Binding> query =
                TPQueryFactory.parse(
                        String.format("PREFIX : <http://rsp4j.io/covid/> "
                                + " PREFIX rsp4j: <http://rsp4j.io/> "
                                + "REGISTER RSTREAM <http://out/stream> AS "
                                + "SELECT ?type  "
                                + " "
                                + " FROM NAMED WINDOW rsp4j:window ON <%s> [RANGE PT1S STEP PT1S] "

                                + "WHERE {"
                                + "   WINDOW rsp4j:window { ?s a ?type .}"

                                + "} ",filePath));

        // Create the RSP4J Task and Continuous Program
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskOperatorAPIImpl.QueryTaskBuilder().fromQuery(query).build();

        ContinuousProgram<Graph, Graph, Binding, Binding> cp =
                new ContinuousProgram.ContinuousProgramBuilder()
                        .in(fileSource)
                        .addTask(t)
                        .out(query.getOutputStream())
                        .addJoinAlgorithm(new HashJoinAlgorithm())
                        .build();
        // Add the Consumer to the stream
        query.getOutputStream().addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
    }
}
