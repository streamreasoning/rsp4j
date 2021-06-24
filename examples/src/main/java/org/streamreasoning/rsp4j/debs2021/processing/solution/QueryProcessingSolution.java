package org.streamreasoning.rsp4j.debs2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.QueryTask;
import org.streamreasoning.rsp4j.abstraction.Task;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.formatter.InstResponseSysOutFormatter;
import org.streamreasoning.rsp4j.yasper.querying.operators.DummyR2R;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;
import org.streamreasoning.rsp4j.yasper.querying.syntax.SimpleRSPQLQuery;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

/***
 * In this exercise we will learn how to query the color stream using RSPQL
 */
public class QueryProcessingSolution {

    public static void main(String[] args) throws InterruptedException {
        // Setup the stream generator
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");



        // Define the query
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER RSTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT2S STEP PT2S] " +
                "WHERE {" +
                "   WINDOW <http://test/window> { ?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://test/Green> .}" +
                "}");


        Task<Graph,Graph,Binding,Binding> t =
                new QueryTask.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph,Graph,Binding,Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(inputStream)
                .addTask(t)
                .out(query.getOutputStream())
                .build();



        query.getOutputStream().addConsumer((el,ts) -> System.out.println(el + " @ " + ts));
        generator.startStreaming();
        Thread.sleep(20_000);
        generator.stopStreaming();
    }

}
