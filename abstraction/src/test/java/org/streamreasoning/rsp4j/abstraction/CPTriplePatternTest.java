package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.junit.Test;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.TableRow;
import org.streamreasoning.rsp4j.abstraction.table.TableRowStream;
import org.streamreasoning.rsp4j.abstraction.triplepattern.ContinuousTriplePatternQuery;
import org.streamreasoning.rsp4j.abstraction.triplepattern.TriplePatternR2R;
import org.streamreasoning.rsp4j.abstraction.utils.DummyConsumer;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;

import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CPTriplePatternTest {

    @Test
    public void simpleTPAbstractionTest() {
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

        StreamToRelationOp<Graph, Graph> build = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());
        build.time().setAppTime(0);

        //SDS
        SDS<Graph> sds = new SDSImpl();
        //R2R
        ContinuousTriplePatternQuery q = new ContinuousTriplePatternQuery("q1", "stream1", "?green rdf:type <http://color#Green>");

        RelationToRelationOperator<TableRow, TableRow> r2r = new TriplePatternR2R(sds, q);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        Task<Graph, Graph,TableRow, TableRow> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", build, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<TableRow,TableRow>())
                        .build();
        ContinuousProgram<Graph, Graph, TableRow,TableRow> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .setSDS(sds)
                .addTask(t)
                .out(outStream)
                .build();

        DummyConsumer<TableRow> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);


        //RUNTIME DATA
        populateStream(stream, build.time().getAppTime());


        assertEquals(2, dummyConsumer.getSize());
        List<TableRow> expected = new ArrayList<>();
        expected.add(new TableRow("green", "<S1>"));
        expected.add(new TableRow("green", "<S4>"));
        assertEquals(expected, dummyConsumer.getReceived());
    }

    @Test
    public void simpleTPAbstractionAggregationTest() {
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
        StreamToRelationOp<Graph, Graph> build = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());


        //SDS
        SDS<Graph> sds = new SDSImpl();
        //R2R
        ContinuousTriplePatternQuery q = new ContinuousTriplePatternQuery("q1", "stream1", "?green rdf:type <http://color#Green>");

        RelationToRelationOperator<TableRow, TableRow> r2r = new TriplePatternR2R(sds, q);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        Task<Graph, Graph, TableRow,TableRow> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", build, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<TableRow,TableRow>())
                        // comment this one out so you can see it works witouth aggregation as well
                        .aggregate("gw", "COUNT", "green", "count")
                        .build();
        ContinuousProgram<Graph, Graph, TableRow, TableRow> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .setSDS(sds)
                .addTask(t)
                .out(outStream)
                .build();

        DummyConsumer<TableRow> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);

        populateStream(stream, build.time().getAppTime());


        assertEquals(3, dummyConsumer.getSize());
        List<TableRow> expected = new ArrayList<>();
        expected.add(new TableRow("count", "0"));
        expected.add(new TableRow("count", "1"));
        expected.add(new TableRow("count", "1"));
        assertEquals(expected, dummyConsumer.getReceived());
    }


    @Test
    public void triplePatternQueryTest(){

//        RDFStream stream = new RDFStream("http://test/stream");
//
//
//        ContinuousQuery<Graph, Graph, Binding> query = TPQueryFactory.parse("" +
//                "REGISTER ISTREAM <http://out/stream> AS " +
//                "SELECT * " +
//                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT2S STEP PT2S] " +
//                "WHERE {" +
//                "   ?green rdf:type <http://color#Green> ." +
//                "}");
//
//
//        //SDS
//        SDS<Graph> sds = new SDSImpl();
//        Task<Graph,Graph,Binding> t =
//                new QueryTask.QueryTaskBuilder()
//                        .fromQuery(query)
//                        .build();
//        ContinuousProgram<Graph,Graph,Triple> cp = new ContinuousProgram.ContinuousProgramBuilder()
//                .in(stream)
//                .setSDS(sds)
//                .addTask(t)
//                .out(query.getOutputStream())
//                .build();
//
//        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
//
//        query.getOutputStream().addConsumer(dummyConsumer);
//
//        populateStream(stream,10000);
//
//
//
//
//
//
//        assertEquals(2,dummyConsumer.getSize());
//        List<TableRow> expected = new ArrayList<>();
//        expected.add(new TableRow("green","<S1>"));
//        expected.add(new TableRow("green","<S4>"));
//        assertEquals(expected,dummyConsumer.getReceived());

    }

    private void populateStream(DataStream<Graph> stream, long startTime) {

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 1000 + startTime);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 1999 + startTime);


        //cp.eval(1999l);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 2001 + startTime);


        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 3000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 5000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 5000 + startTime);
        stream.put(graph, 6000 + startTime);
    }
}
