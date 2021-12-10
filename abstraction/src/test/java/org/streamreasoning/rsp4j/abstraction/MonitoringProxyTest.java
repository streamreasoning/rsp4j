package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.junit.Test;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.monitoring.Metric;
import org.streamreasoning.rsp4j.abstraction.monitoring.MonitoringR2RProxy;
import org.streamreasoning.rsp4j.abstraction.monitoring.RSP4JMonitor;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.abstraction.utils.DummyConsumer;
import org.streamreasoning.rsp4j.abstraction.utils.DummyStream;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MonitoringProxyTest {

    @Test
    public void testR2RMonitor(){
        DataStream<Metric> metricStream = RSP4JMonitor.getMonitoringStream();
        DummyConsumer<Metric> dummyConsumer = new DummyConsumer<>();
        metricStream.addConsumer(dummyConsumer);
        TP tp = new TP("?green", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://color#Green");
        String componentName = "TriplePatternR2R";
        RelationToRelationOperator<Graph,Binding> monitorProxy = new MonitoringR2RProxy<>(tp,componentName);
        //create data
        Graph greenGraph = DummyStream.createSingleColorGraph("http://color#","Green");
        monitorProxy.eval(Stream.of(greenGraph));

        assertEquals(1,dummyConsumer.getSize());
        Metric metricResult = dummyConsumer.getReceived().get(0);


        assertEquals(componentName,metricResult.getComponentName());
        assertEquals("eval",metricResult.getFunctionName());
        assertEquals("EvalTime",metricResult.getMetricName());
        assertTrue(metricResult.getSystemTime()>0);
        assertTrue(metricResult.getMetricResult()>=0);
        System.out.println(metricResult);
    }


    @Test
    public void testMonitorTask(){
        RDFStream stream = new RDFStream("stream1");
        BindingStream outStream = new BindingStream("out");

        DataStream<Metric> metricStream = RSP4JMonitor.getMonitoringStream();
        DummyConsumer<Metric> dummyConsumer = new DummyConsumer<>();
        metricStream.addConsumer(dummyConsumer);

        Report report = new ReportImpl();
        report.add(new OnWindowClose());
        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;
        Time instance = new TimeImpl(0);
        StreamToRelationOp<Graph, Graph> window1 = new CSPARQLStreamToRelationOp<Graph, Graph>(RDFUtils.createIRI("w1"), 2000, 2000, instance, tick, report, report_grain, new GraphContentFactory(instance));


        TP tp = new TP("?green", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://color#Green");

        BGP bgp = BGP.createFrom("?red", "http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://color#Red")
                .build();

        TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
                new TaskAbstractionImpl.MonitoringTaskBuilder<Graph, Graph, Binding, Binding>()
                        .addS2R("stream1", window1, "w1")
                        .addR2R("w1", tp)
                        .addR2R("w1", bgp)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp = new ContinuousProgram.ContinuousProgramBuilder<Graph, Graph, Binding, Binding>()
                .in(stream)
                .addTask(t)
                .addJoinAlgorithm(new NestedJoinAlgorithm())
                .out(outStream)
                .build();

        DummyStream.populateStream(stream, instance.getAppTime());

        assertEquals(9,dummyConsumer.getSize());
        Metric metricResult = dummyConsumer.getReceived().get(0);


        assertEquals("eval",metricResult.getFunctionName());
        assertEquals("EvalTime",metricResult.getMetricName());
        assertTrue(metricResult.getSystemTime()>0);
        assertTrue(metricResult.getMetricResult()>=0);
        dummyConsumer.getReceived().forEach(System.out::println);
    }
}
