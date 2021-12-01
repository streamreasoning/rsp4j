package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.abstraction.utils.DummyConsumer;
import org.streamreasoning.rsp4j.abstraction.utils.DummyStream;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
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
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.NestedJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;
import org.streamreasoning.rsp4j.yasper.querying.syntax.TPQueryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class FilterTest {
    // ENGINE DEFINITION
    private Report report;
    private Tick tick;
    private ReportGrain report_grain;

    private int scope = 0;
    private enum StreamType {VALUES, IRIS};
    public FilterTest() {
        report = new ReportImpl();
        report.add(new OnWindowClose());
        //        report.add(new NonEmptyContent());
        //        report.add(new OnContentChange());
        //        report.add(new Periodic());

        tick = Tick.TIME_DRIVEN;
        report_grain = ReportGrain.SINGLE;
    }

    @Test
    public void iriFilter() {

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
    Filter<Binding> filter =
        new Filter<Binding>(
            Stream.empty(),
                binding -> {
              System.out.println(binding);
              return binding.value(s).equals(new TermImpl("S4"));
            });
        R2RPipe<Graph,Binding> pipe = new R2RPipe<>(tp,filter);
        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
                new TaskAbstractionImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", pipe)
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

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));
        b2.add(new VarImpl("red"), RDFUtils.createIRI("S2"));
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void valueFilter() {

        //STREAM DECLARATION
        RDFStream stream = new RDFStream("stream1");
        BindingStream outStream = new BindingStream("out");


        //WINDOW DECLARATION

        Time instance = new TimeImpl(0);

        StreamToRelationOp<Graph, Graph> window1 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));
        StreamToRelationOp<Graph, Graph> window2 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w2"), 4000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        //R2R
        VarOrTerm s = new VarImpl("obs");
        VarOrTerm p = new TermImpl("http://test/hasValue");
        VarOrTerm o = new VarImpl("value");
        TP tp = new TP(s, p, o);
        Filter<Binding> filter =
                new Filter<Binding>(
                        Stream.empty(),
                        binding -> {
                            System.out.println(binding);
                            return RDFUtils.parseDouble(binding.value(o).ntriplesString())>50;
                        });
        R2RPipe<Graph,Binding> pipe = new R2RPipe<>(tp,filter);
        // REGISTER FUNCTION
        AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

        TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
                new TaskAbstractionImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", pipe)
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
        DummyStream.populateValueStream(stream, instance.getAppTime());

        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b2 = new BindingImpl();
    b2.add(
        new VarImpl("value"),
        RDFUtils.createLiteral("90", RDFUtils.createIRI("http://www.w3.org/2001/XMLSchema#integer")));
        b2.add(new VarImpl("obs"), RDFUtils.createIRI("S6"));
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
   @Test
    public void iriFilterQuery() {

        //STREAM DECLARATION
       DummyConsumer<Binding> dummyConsumer = parseQueryAndGetConsumer("" +
               "REGISTER ISTREAM <http://out/stream> AS " +
               "SELECT ?green " +
               "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
               "WHERE {" +
               "   WINDOW <http://test/window> {?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> . Filter(?green = <http://test/S4>)}" +
               "}", StreamType.IRIS);

       System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("http://test/S4"));
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void varFilterQuery() {
        DummyConsumer<Binding> dummyConsumer = parseQueryAndGetConsumer("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?green ?green2 " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   WINDOW <http://test/window> {?green <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> . " +
                " ?green2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://color#Green> . Filter(?green = ?green2)}" +
                "}", StreamType.IRIS);

        System.out.println(dummyConsumer.getReceived());

        assertEquals(2, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b1 = new BindingImpl();
        b1.add(new VarImpl("green"), RDFUtils.createIRI("http://test/S1"));
        b1.add(new VarImpl("green2"), RDFUtils.createIRI("http://test/S1"));

        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("http://test/S4"));
        b2.add(new VarImpl("green2"), RDFUtils.createIRI("http://test/S4"));
        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void valueFilterQuery() {
        DummyConsumer<Binding> dummyConsumer = parseQueryAndGetConsumer("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?value " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   WINDOW <http://test/window> {?obs <http://test/hasValue> ?value . Filter(?value >40)}" +
                "}", StreamType.VALUES);

        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b1 = new BindingImpl();
        b1.add(
                new VarImpl("value"),
                RDFUtils.createLiteral("50", RDFUtils.createIRI("http://www.w3.org/2001/XMLSchema#integer")));
        expected.add(b1);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void valueMultipleFilterQuery() {
        DummyConsumer<Binding> dummyConsumer = parseQueryAndGetConsumer("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?value " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   WINDOW <http://test/window> {?obs <http://test/hasValue> ?value . ?obs2 <http://test/hasValue> ?value2 . Filter(?value2 >=40). Filter(?value > value2})" +
                "}", StreamType.VALUES);

        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b1 = new BindingImpl();
        b1.add(
                new VarImpl("value"),
                RDFUtils.createLiteral("50", RDFUtils.createIRI("http://www.w3.org/2001/XMLSchema#integer")));
        expected.add(b1);
        assertEquals(expected, dummyConsumer.getReceived());
    }
    @Test
    public void valueConjunctionFilterQuery() {
        DummyConsumer<Binding> dummyConsumer = parseQueryAndGetConsumer("" +
                "REGISTER ISTREAM <http://out/stream> AS " +
                "SELECT ?value " +
                "FROM NAMED WINDOW <http://test/window> ON <http://test/stream> [RANGE PT10S STEP PT5S] " +
                "WHERE {" +
                "   WINDOW <http://test/window> {?obs <http://test/hasValue> ?value . ?obs2 <http://test/hasValue> ?value2 . Filter(?value2 >=40 && ?value > ?value2})" +
                "}", StreamType.VALUES);

        System.out.println(dummyConsumer.getReceived());

        assertEquals(1, dummyConsumer.getSize());
        List<Binding> expected = new ArrayList<>();

        Binding b1 = new BindingImpl();
        b1.add(
                new VarImpl("value"),
                RDFUtils.createLiteral("50", RDFUtils.createIRI("http://www.w3.org/2001/XMLSchema#integer")));
        expected.add(b1);
        assertEquals(expected, dummyConsumer.getReceived());
    }

    private DummyConsumer<Binding> parseQueryAndGetConsumer(String s, StreamType streamType) {
        //STREAM DECLARATION
        RDFStream stream = new RDFStream("http://test/stream");


        //WINDOW DECLARATION

        ContinuousQuery<Graph, Graph, Binding, Binding> query = TPQueryFactory.parse(s);

        TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
                new QueryTaskAbstractionImpl.QueryTaskBuilder()
                        .fromQuery(query)
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(query.getOutputStream())
                .build();
        DataStream<Binding> outStream = cp.outstream();

        DummyConsumer<Binding> dummyConsumer = new DummyConsumer<>();
        outStream.addConsumer(dummyConsumer);

        Time instance = new TimeImpl(0);
        //RUNTIME DATA
        switch(streamType){
            case IRIS:
                DummyStream.populateStream(stream, instance.getAppTime(),"http://test/");
                break;
            case VALUES:
                DummyStream.populateValueStream(stream, instance.getAppTime(),"http://test/");
                break;
        }

        return dummyConsumer;
    }
}
