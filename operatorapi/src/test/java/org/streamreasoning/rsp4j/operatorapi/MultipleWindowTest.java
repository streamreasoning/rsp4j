package org.streamreasoning.rsp4j.operatorapi;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.operatorapi.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.operatorapi.functions.CountFunction;
import org.streamreasoning.rsp4j.operatorapi.table.BindingStream;
import org.streamreasoning.rsp4j.operatorapi.utils.DummyConsumer;
import org.streamreasoning.rsp4j.operatorapi.utils.DummyStream;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.io.sources.FileSource;
import org.streamreasoning.rsp4j.io.utils.RDFBase;
import org.streamreasoning.rsp4j.io.utils.parsing.JenaRDFCommonsParsingStrategy;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.NestedJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MultipleWindowTest {

    // ENGINE DEFINITION
    private Report report;
    private Tick tick;
    private ReportGrain report_grain;

    private int scope = 0;

    public MultipleWindowTest() {
        report = new ReportImpl();
        report.add(new OnWindowClose());
        //        report.add(new NonEmptyContent());
        //        report.add(new OnContentChange());
        //        report.add(new Periodic());

        tick = Tick.TIME_DRIVEN;
        report_grain = ReportGrain.SINGLE;
    }

    @Test
    public void multipleWindowTestSameStream() {

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION

        Time instance = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> window1 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));
        StreamToRelationOp<Graph, Graph> window2 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w2"), 4000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        //R2R
        VarOrTerm s = new VarImpl("green");
        VarOrTerm s2 = new VarImpl("red");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://color#Green");
        VarOrTerm o2 = new TermImpl("http://color#Red");
        TP tp = new TP(s, p, o);
        TP tp2 = new TP(s2, p, o2);

        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new TaskOperatorAPIImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", tp)
                        .addS2R("stream1", window2, "w2")
                        .addR2R("w2", tp2)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(outStream)
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);


        //RUNTIME DATA
        DummyStream.populateStream(stream, instance.getAppTime());

        System.out.println(dummyConsumer.getReceived());

        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        b1.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b2.add(new VarImpl("red"), RDFUtils.createIRI("S2"));

        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void multipleWindowTestMultipleStream() {

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        RDFStream stream2 = new RDFStream("stream2");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION

        Time instance = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> window1 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));
        StreamToRelationOp<Graph, Graph> window2 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w2"), 4000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        //R2R
        VarOrTerm s = new VarImpl("green");
        VarOrTerm s2 = new VarImpl("red");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://color#Green");
        VarOrTerm o2 = new TermImpl("http://color#Red");
        TP tp = new TP(s, p, o);
        TP tp2 = new TP(s2, p, o2);

        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new TaskOperatorAPIImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", tp)
                        .addS2R("stream2", window2, "w2")
                        .addR2R("w2", tp2)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .in(stream2)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(outStream)
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);


        //RUNTIME DATA
        populateMultipleStreams(stream,stream2, instance.getAppTime());

        System.out.println(dummyConsumer.getReceived());

        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        b1.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b2.add(new VarImpl("red"), RDFUtils.createIRI("S2"));

        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }

    @Test
    public void multipleWindowTestMultipleStreamWithJoin() {

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        RDFStream stream2 = new RDFStream("stream2");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION

        Time instance = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> window1 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));
        StreamToRelationOp<Graph, Graph> window2 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w2"), 4000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        //R2R
        VarOrTerm s = new VarImpl("blue");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://color#Blue");
        TP tp = new TP(s, p, o);
        TP tp2 = new TP(s, p, o);

        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new TaskOperatorAPIImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", tp)
                        .addS2R("stream2", window2, "w2")
                        .addR2R("w2", tp2)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .in(stream2)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(outStream)
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);


        //RUNTIME DATA
        populateMultipleStreams(stream,stream2, instance.getAppTime());

        System.out.println(dummyConsumer.getReceived());

        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("blue"), RDFUtils.createIRI("S3"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("blue"), RDFUtils.createIRI("S5"));

        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }

    @Test
    public void multipleWindowFromQueryTest() {

        Time instance = new TimeImpl(0);

        RDFStream stream = new RDFStream("http://test/stream");

    ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
            ""
                + "REGISTER ISTREAM <http://out/stream> AS "
                + "SELECT * "
                + "FROM NAMED WINDOW <window1> ON <http://test/stream> [RANGE PT2S STEP PT2S] "
                + "FROM NAMED WINDOW <window2> ON <http://test/stream> [RANGE PT4S STEP PT2S] "
                + "WHERE {"
                + "  WINDOW <window1> {?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green>.}"
                + "  WINDOW <window2> {?red <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Red>.}"
                + "}");

        //SDS
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskOperatorAPIImpl.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(query.getOutputStream())
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        query.getOutputStream().addConsumer(dummyConsumer);


        DummyStream.populateStream(stream, instance.getAppTime());



        System.out.println(dummyConsumer.getReceived());

        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        b1.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b2.add(new VarImpl("red"), RDFUtils.createIRI("S2"));

        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void multipleWindowFromQueryTestWithStaticData() {

        Time instance = new TimeImpl(0);

        RDFStream stream = new RDFStream("http://test/stream");
        URL fileURL = MultipleWindowTest.class.getClassLoader().getResource(
                "colors.nt");
        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM <" + fileURL.getPath() + "> "
                + "FROM NAMED WINDOW <window1> ON <http://test/stream> [RANGE PT2S STEP PT2S] "
                + "FROM NAMED WINDOW <window2> ON <http://test/stream> [RANGE PT4S STEP PT2S] "
                + "WHERE {" +
                " ?green <http://color#source> ?source. ?red <http://color#source> ?source " +
                "  WINDOW <window1> {?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green>.}" +
                "  WINDOW <window2> {?red <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Red>.}" +
                "}");

        //SDS
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskOperatorAPIImpl.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(query.getOutputStream())
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        query.getOutputStream().addConsumer(dummyConsumer);


        DummyStream.populateStream(stream, instance.getAppTime());



        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b1.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        b1.add(new VarImpl("source"), RDFUtils.createIRI("Source2"));


        expected.add(b1);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void multipleWindowFromQueryTestWithStaticDataAndFileSources() throws InterruptedException {
        URL fileURL = MultipleWindowTest.class.getClassLoader().getResource(
                "colors.nt");
        URL streamURL = MultipleWindowTest.class.getClassLoader().getResource(
                "stream1.nt");
        Time instance = new TimeImpl(0);

        // create parsing strategy
        JenaRDFCommonsParsingStrategy parsingStrategy = new JenaRDFCommonsParsingStrategy(RDFBase.NT);
        // create file source to read the newly created file
        FileSource<Graph> stream1 = new FileSource<Graph>(streamURL.getPath(), 1000, parsingStrategy);
        FileSource<Graph> stream2 = new FileSource<Graph>(streamURL.getPath(), 1000, parsingStrategy);

        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM <" + fileURL.getPath() + "> "
                + "FROM NAMED WINDOW <window1> ON <"+streamURL.getPath()+"> [RANGE PT2S STEP PT2S] "
                + "FROM NAMED WINDOW <window2> ON  <"+streamURL.getPath() +">[RANGE PT4S STEP PT2S] "
                + "WHERE {" +
                " ?green <http://color#source> ?source. ?red <http://color#source> ?source " +
                "  WINDOW <window1> {?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green>.}" +
                "  WINDOW <window2> {?red <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Red>.}" +
                "}");

        //SDS
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskOperatorAPIImpl.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream1)
                .in(stream2)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(query.getOutputStream())
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        query.getOutputStream().addConsumer(dummyConsumer);
        stream1.stream();
        stream2.stream();
        Thread.sleep(7000);



        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b1.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        b1.add(new VarImpl("source"), RDFUtils.createIRI("Source2"));


        expected.add(b1);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    //@Test
    public void testLargeStatic() throws InterruptedException {

        Time instance = new TimeImpl(0);

        // create parsing strategy
        JenaRDFCommonsParsingStrategy parsingStrategy = new JenaRDFCommonsParsingStrategy(RDFBase.N3);
        // create file source to read the newly created file
        RDFStream stream = new RDFStream("http://test/stream");


        ContinuousQuery<Graph, Graph, Binding, Binding> query =
        TPQueryFactory.parse(
            ""
                + "REGISTER ISTREAM <http://out/stream> AS "
                + "SELECT * "
                + "FROM </Users/psbonte/Documents/Github/CityBench_yasper_tmp/dataset/SensorRepository.n3> "
                + "WHERE {"
                + " ?p1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.insight-centre.org/citytraffic#CongestionLevel> . "
                + "    ?p2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.insight-centre.org/citytraffic#CongestionLevel> . "
                + "}");

        //SDS
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskOperatorAPIImpl.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(query.getOutputStream())
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        query.getOutputStream().addConsumer(dummyConsumer);
        while (true) {
            populateStream(stream, 0 );

            Thread.sleep(1000);
        }



    }

    private void populateStream(DataStream<Graph> stream, long startTime) {


        DummyStream.populateStream(stream, startTime);
    }
    private void populateMultipleStreams(DataStream<Graph> stream, DataStream<Graph> stream2,long startTime) {

        //RUNTIME DATA

        RDF instance = RDFUtils.getInstance();
        Graph graph = instance.createGraph();
        IRI p = instance.createIRI("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        graph.add(instance.createTriple(instance.createIRI("S1"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 1000 + startTime);

        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S2"), p, instance.createIRI("http://color#Red")));
        stream2.put(graph, 1999 + startTime);


        //cp.eval(1999l);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S3"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 2001 + startTime);
        stream2.put(graph, 2001 + startTime);


        graph = instance.createGraph();

        graph.add(instance.createTriple(instance.createIRI("S4"), p, instance.createIRI("http://color#Green")));
        stream.put(graph, 3000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S5"), p, instance.createIRI("http://color#Blue")));
        stream.put(graph, 5000 + startTime);
        stream2.put(graph, 5000 + startTime);


        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S6"), p, instance.createIRI("http://color#Red")));
        stream2.put(graph, 5000 + startTime);
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("http://color#Red")));
        stream2.put(graph, 6001 + startTime);
    }
}
