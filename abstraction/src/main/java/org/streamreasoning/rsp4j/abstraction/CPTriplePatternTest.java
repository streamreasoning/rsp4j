package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.TableRow;
import org.streamreasoning.rsp4j.abstraction.table.TableRowStream;
import org.streamreasoning.rsp4j.abstraction.table.TableRowsSysOutFormatter;
import org.streamreasoning.rsp4j.abstraction.triplepattern.ContinuousTriplePatternQuery;
import org.streamreasoning.rsp4j.abstraction.triplepattern.TriplePatternR2R;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

public class CPTriplePatternTest {

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
        TableRowStream outStream = new TableRowStream("out");


        //WINDOW DECLARATION
        StreamToRelationOperatorFactory<Graph, Graph> windowOperatorFactory = new CSPARQLTimeWindowOperatorFactory( TimeFactory.getInstance(), tick, report, report_grain);

        StreamToRelationOp<Graph, Graph> build = windowOperatorFactory.build(2000, 2000, scope);

        //SDS
        SDS<Graph> sds = new SDSImpl();
        //R2R
        ContinuousTriplePatternQuery q = new ContinuousTriplePatternQuery("q1","stream1","?green rdf:type <http://color#Green>");

        RelationToRelationOperator<TableRow> r2r = new TriplePatternR2R(sds, q);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT",new CountFunction());

        Task<Graph,Graph,Triple> t =
        new Task.TaskBuilder()
            .addS2R("stream1", build, "w1")
            .addR2R("w1", r2r)
            .addR2S("out", new Rstream<TableRow>())
                // comment this one out so you can see it works witouth aggregation as well
            .aggregate("gw","COUNT","green", "count")
            .build();
        ContinuousProgram<Graph,Graph,Triple> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .setSDS(sds)
                .addTask(t)
                .out(outStream)
                .build();

        outStream.addConsumer(new TableRowsSysOutFormatter("TTL", true));


        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 1000);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 1999);


        //cp.eval(1999l);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 2001);



        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 3000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 5000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 5000);
        stream.put(graph, 6000);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 7000);


    }
}
