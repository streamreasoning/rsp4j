package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.junit.Test;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.abstraction.utils.DummyConsumer;
import org.streamreasoning.rsp4j.abstraction.utils.DummyStream;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.examples.RDFStream;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.joins.NestedJoinAlgorithm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProjectionTest {
    // ENGINE DEFINITION
    private Report report;
    private Tick tick;
    private ReportGrain report_grain;

    private int scope = 0;

    public ProjectionTest() {
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

        TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
                new TaskAbstractionImpl.TaskBuilder()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", tp)
                        .addS2R("stream1", window2, "w2")
                        .addR2R("w2", tp2)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .addProjection(Collections.singletonList(new VarImpl("green")))
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
        Binding b2 = new BindingImpl();
        b2.add(new VarImpl("green"), RDFUtils.createIRI("S4"));

        expected.add(b1);
        expected.add(b2);
        assertEquals(expected, dummyConsumer.getReceived());
    }
}
