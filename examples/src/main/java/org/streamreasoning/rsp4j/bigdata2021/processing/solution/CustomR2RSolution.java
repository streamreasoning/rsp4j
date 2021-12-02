package org.streamreasoning.rsp4j.bigdata2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.TaskAbstractionImpl;
import org.streamreasoning.rsp4j.abstraction.functions.AggregationFunctionRegistry;
import org.streamreasoning.rsp4j.abstraction.functions.CountFunction;
import org.streamreasoning.rsp4j.abstraction.table.BindingStream;
import org.streamreasoning.rsp4j.api.operators.r2r.utils.R2RPipe;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.secret.time.TimeImpl;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.reasoning.csprite.R2RUpwardExtension;
import org.streamreasoning.rsp4j.reasoning.csprite.UpwardExtension;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomR2RSolution {

  public static void main(String[] args) throws InterruptedException {
    StreamGenerator generator = new StreamGenerator();
    DataStream<Graph> inputStream = generator.getStream("http://test/stream");

    // define output stream
    BindingStream outStream = new BindingStream("out");

    // Engine properties
    Report report = new ReportImpl();
    report.add(new OnWindowClose());
    //        report.add(new NonEmptyContent());
    //        report.add(new OnContentChange());
    //        report.add(new Periodic());

    Tick tick = Tick.TIME_DRIVEN;
    ReportGrain report_grain = ReportGrain.SINGLE;
    Time instance = new TimeImpl(0);

    // WINDOW DECLARATION
    StreamToRelationOp<Graph, Graph> build =
        new CSPARQLStreamToRelationOp<>(
            RDFUtils.createIRI("w1"),
            2000,
            2000,
            instance,
            tick,
            report,
            report_grain,
            new GraphContentFactory(instance));

    // R2R
    VarOrTerm s = new VarImpl("color");
    VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
    VarOrTerm o = new VarImpl("type");

    Map<String, List<String>> schema = new HashMap<>();
    schema.put("http://test/Warm", Arrays.asList("http://test/Green", "http://test/Orange","http://test/Yellow","http://test/Red","http://test/White"));
    schema.put("http://test/Cool", Arrays.asList("http://test/Green", "http://test/Blue","http://test/Violet","http://test/Red","http://test/Black","http://test/Grey"));

    UpwardExtension upwardExtension = new UpwardExtension(schema);
    System.out.println(upwardExtension.getUpwardExtension("http://test/Yellow")); // [http://test/Warm]
    System.out.println(upwardExtension.getUpwardExtension("http://test/Green"));  // [http://test/Cool, http://test/Warm]

    RelationToRelationOperator<Graph,Graph> upwardExtensionR2R = new R2RUpwardExtension(upwardExtension);
    TP tp = new TP(s, p, o);
    R2RPipe<Graph,Binding> r2r = new R2RPipe<>(upwardExtensionR2R,tp);
    // REGISTER FUNCTION
    AggregationFunctionRegistry.getInstance().addFunction("COUNT", new CountFunction());

    TaskAbstractionImpl<Graph, Graph, Binding, Binding> t =
        new TaskAbstractionImpl.TaskBuilder()
            .addS2R("stream1", build, "w1")
            .addR2R("w1", r2r)
            .addR2S("out", new Rstream<Binding, Binding>())
            .build();
    ContinuousProgram<Graph, Graph, Binding, Binding> cp =
        new ContinuousProgram.ContinuousProgramBuilder()
            .in(inputStream)
            .addTask(t)
            .out(outStream)
            .build();

    outStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
    generator.startStreaming();
    Thread.sleep(20_000);
    generator.stopStreaming();
  }
}
