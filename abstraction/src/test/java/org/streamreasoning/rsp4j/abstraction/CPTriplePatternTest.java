package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
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
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;

import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.Binding;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.BindingImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CPTriplePatternTest {

  // ENGINE DEFINITION
    private Report report;
    private Tick tick ;
    private ReportGrain report_grain;

    private int scope = 0;
    public CPTriplePatternTest() {
        report = new ReportImpl();
        report.add(new OnWindowClose());
        //        report.add(new NonEmptyContent());
        //        report.add(new OnContentChange());
        //        report.add(new Periodic());

        tick = Tick.TIME_DRIVEN;
        report_grain = ReportGrain.SINGLE;
    }




    @Test
    public void simpleTPAbstractionTest() {


        //QUERY


        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION

        StreamToRelationOp<Graph, Graph> build = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());


        //R2R
        ContinuousTriplePatternQuery q = new ContinuousTriplePatternQuery("q1", "stream1", "?green rdf:type <http://color#Green>");

        RelationToRelationOperator<Graph, Binding> r2r = new TriplePatternR2R(q);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        Task<Graph, Graph,Binding, Binding> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", build, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<Binding,Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding,Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .out(outStream)
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);


        //RUNTIME DATA
        populateStream(stream, TimeFactory.getInstance().getAppTime());


        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }

    @Test
    public void simpleTPAbstractionAggregationTest() {

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION
        StreamToRelationOp<Graph, Graph> build = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());

        //R2R
        ContinuousTriplePatternQuery q = new ContinuousTriplePatternQuery("q1", "stream1", "?green rdf:type <http://color#Green>");

        RelationToRelationOperator<Graph, Binding> r2r = new TriplePatternR2R( q);


        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        Task<Graph, Graph, Binding,Binding> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", build, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<Binding,Binding>())
                        // comment this one out so you can see it works witouth aggregation as well
                        .aggregate("gw", "COUNT", "green", "count")
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .out(outStream)
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);

        populateStream(stream, TimeFactory.getInstance().getAppTime());


        assertEquals(3, dummyConsumer.getSize());

        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("count"), RDFUtils.createIRI("1"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("count"), RDFUtils.createIRI("1"));
        Binding b3 = new BindingImpl();
        b3.add(new VarImpl("count"), RDFUtils.createIRI("0"));
        expected.add(b1);
        expected.add(b2);
        expected.add(b3);
        assertEquals(expected, dummyConsumer.getReceived());
    }


    @Test
    public void triplePatternQueryTest(){

        RDFStream stream = new RDFStream("http://test/stream");


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER RSTREAM <http://out/stream> AS " +
                "SELECT * " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT2S STEP PT2S] " +
                "WHERE {" +
                "   ?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> ." +
                "}");


        //SDS
        Task<Graph,Graph,Binding,Binding> t =
                new QueryTask.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph,Graph,Binding,Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .out(query.getOutputStream())
                .build();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        query.getOutputStream().addConsumer(dummyConsumer);

        populateStream(stream,TimeFactory.getInstance().getAppTime());






        assertEquals(2,dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        expected.add(b1);
        expected.add(b2);

        assertEquals(expected,dummyConsumer.getReceived());

    }

    @Test
    public void triplePatternQueryNoWindowTest(){

        RDFStream stream = new RDFStream("http://test/stream");


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "REGISTER RSTREAM <http://out/stream> AS " +
                "SELECT * " +
                "WHERE {" +
                "   ?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> ." +
                "}");


        //SDS
        SDS<Graph> sds = new SDSImpl();
        // Add S2R
        StreamToRelationOp<Graph, Graph> r2r = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());
        TimeVarying<Graph> tvg =  r2r.apply(stream);
        sds.add(tvg);


        Task<Graph,Graph,Binding,Binding> t =
                new QueryTask.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph,Graph,Binding,Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .setSDS(sds)
                .out(query.getOutputStream())
                .build();

        r2r.link(cp); //TODO make sure the R2R is linked in the CP

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        query.getOutputStream().addConsumer(dummyConsumer);

        populateStream(stream,TimeFactory.getInstance().getAppTime());






        assertEquals(2,dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        expected.add(b1);
        expected.add(b2);

        assertEquals(expected,dummyConsumer.getReceived());

    }
    @Test
    public void triplePatternQueryNoWindowNoRegisterTest(){

        RDFStream stream = new RDFStream("http://test/stream");
        BindingStream outStream = new BindingStream("out");


        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse("" +
                "SELECT * " +
                "WHERE {" +
                "   ?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> ." +
                "}");


        //SDS
        SDS<Graph> sds = new SDSImpl();
        // Add S2R
        StreamToRelationOp<Graph, Graph> r2r = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, TimeFactory.getInstance(), tick, report, report_grain, new GraphContentFactory());
        TimeVarying<Graph> tvg =  r2r.apply(stream);
        sds.add(tvg);


        Task<Graph,Graph,Binding,Binding> t =
                new QueryTask.QueryTaskBuilder()
                        .fromQuery(query)
                        .addR2S("", Rstream.get())
                        .build();
        ContinuousProgram<Graph,Graph,Binding,Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .setSDS(sds)
                .out(outStream)
                .build();

        r2r.link(cp); //TODO make sure the r2r is linked in the CP

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();

        outStream.addConsumer(dummyConsumer);

        populateStream(stream,TimeFactory.getInstance().getAppTime());






        assertEquals(2,dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();
        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("S1"));
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        expected.add(b1);
        expected.add(b2);

        assertEquals(expected,dummyConsumer.getReceived());

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
        graph = instance.createGraph();
        graph.add(instance.createTriple(instance.createIRI("S7"), p, instance.createIRI("http://color#Red")));
        stream.put(graph, 6001 + startTime);
    }
}
