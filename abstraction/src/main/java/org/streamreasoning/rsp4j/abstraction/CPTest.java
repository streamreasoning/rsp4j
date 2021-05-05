package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.streamreasoning.rsp4j.yasper.ContinuousQueryExecutionImpl;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.examples.RDFTripleStream;
import org.streamreasoning.rsp4j.yasper.querying.formatter.ContinuousQueryImpl;
import org.streamreasoning.rsp4j.yasper.querying.formatter.InstResponseSysOutFormatter;
import org.streamreasoning.rsp4j.yasper.querying.operators.R2RImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

public class CPTest {

    public static void main(String[] args){
        //ENGINE DEFINITION
        Report report = new ReportImpl();
        report.add(new OnWindowClose());
//        report.add(new NonEmptyContent());
//        report.add(new OnContentChange());
//        report.add(new Periodic());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        int scope = 0;

        //QUERY


        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        RDFTripleStream outStream = new RDFTripleStream("out");


        //WINDOW DECLARATION
        StreamToRelationOperatorFactory<Graph, Graph> windowOperator = new CSPARQLTimeWindowOperatorFactory(2000, 2000, scope, TimeFactory.getInstance(), tick, report, report_grain, null);

        //SDS
        SDS sds = new SDSImpl();
        //R2R
        ContinuousQuery q = new ContinuousQueryImpl("q1");

        RelationToRelationOperator r2r = new R2RImpl(sds, q);

        Task t = new Task.TaskBuilder()
                .addSource(stream)
                .addS2R("stream1",windowOperator,"w1")
                .addR2R("w1",r2r)
                .addR2S("out",new Rstream())
                .addSDS(sds)
                .addContinuousQuery(q)
                .addSink(outStream)
                .build();
        ContinuousProgram cp = new ContinuousProgram.ContinuousProgramBuilder()
                .addTask(t)
                .build();
        ContinuousQueryExecution<Graph, Graph, Triple> cqe = cp.getContinuousQueryExecution();
        WebDataStream<Triple> outstream = cqe.outstream();
        outstream.addConsumer(new InstResponseSysOutFormatter("TTL", true));

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("p");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("O1")));
        stream.put(graph, 1000);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("O2")));
        stream.put(graph, 1999);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("O3")));
        stream.put(graph, 2001);

        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("O4")));
        stream.put(graph, 3000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("O5")));
        stream.put(graph, 5000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("O6")));
        stream.put(graph, 5000);
        stream.put(graph, 6000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("O7")));
        stream.put(graph, 7000);


    }
}
