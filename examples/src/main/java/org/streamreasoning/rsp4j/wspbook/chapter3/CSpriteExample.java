package org.streamreasoning.rsp4j.wspbook.chapter3;

import org.apache.commons.rdf.api.Graph;
import org.streamreasoning.rsp4j.operatorapi.ContinuousProgram;
import org.streamreasoning.rsp4j.operatorapi.TaskOperatorAPIImpl;
import org.streamreasoning.rsp4j.operatorapi.table.BindingStream;
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
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.reasoning.csprite.CSpriteR2R;
import org.streamreasoning.rsp4j.reasoning.csprite.HierarchySchema;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.*;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

public class CSpriteExample {

    public static void main(String[] args) throws InterruptedException {
        // define the generator and input stream
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("http://test/stream");
        // define output stream
        BindingStream outStream = new BindingStream("out");
        //create the window operator
        StreamToRelationOp<Graph, Graph> windowOperator = createWindowOperator(2000, 2000, "w1");
        // create a simple hierarchy
        HierarchySchema hierarchySchema = getHierarchySchema();
        // create a simple triple pattern
        RelationToRelationOperator<Graph, Binding> tp = createTriplePattern();
        // create the CSprite R2R
        RelationToRelationOperator<Graph, Graph> cSpriteR2R = new CSpriteR2R(tp, hierarchySchema);
        // create a R2R pipeline to combine CSprite and the TP evaluation
        R2RPipe<Graph,Binding> r2rPipe = new R2RPipe<>(cSpriteR2R,tp);


        // define the task and CP
        TaskOperatorAPIImpl<Graph, Graph, Binding, Binding> t =
                new TaskOperatorAPIImpl.TaskBuilder()
                        .addS2R("http://test/stream", windowOperator, "w1")
                        .addR2R("w1", r2rPipe)
                        .addR2S("out", new Rstream<Binding, Binding>())
                        .build();
        ContinuousProgram<Graph, Graph, Binding, Binding> cp =
                new ContinuousProgram.ContinuousProgramBuilder()
                        .in(inputStream)
                        .addTask(t)
                        .out(outStream)
                        .build();
        // add the consumer
        outStream.addConsumer((el, ts) -> System.out.println(el + " @ " + ts));
        // start streaming
        generator.startStreaming();
        Thread.sleep(20_000);
        generator.stopStreaming();


    }

    private static RelationToRelationOperator<Graph, Binding> createTriplePattern() {
        VarOrTerm s = new VarImpl("warmColor");
        VarOrTerm p = new TermImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
        VarOrTerm o = new TermImpl("http://test/Warm");
        RelationToRelationOperator<Graph, Binding> tp = new TP(s, p, o);
        return tp;
    }

    private static StreamToRelationOp<Graph, Graph> createWindowOperator(int width, int slide, String windowName) {
        // Engine properties
        Report report = ReportImpl.fromStrategies(new OnWindowClose());
        Time timeInstance = TimeImpl.forStartTime(0);

        // WINDOW DECLARATION
        StreamToRelationOp<Graph, Graph> windowOperator =
                new CSPARQLStreamToRelationOp<>(
                        RDFUtils.createIRI(windowName),
                        width,
                        slide,
                        timeInstance,
                        Tick.TIME_DRIVEN,
                        report,
                        ReportGrain.SINGLE,
                        new GraphContentFactory(timeInstance));
        return windowOperator;
    }

    private static HierarchySchema getHierarchySchema() {
        HierarchySchema hierarchySchema = new HierarchySchema();
        hierarchySchema.addSubClassOf("http://test/Green","http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Orange","http://test/Warm");
        hierarchySchema.addSubClassOf("http://test/Blue","http://test/Cool");
        hierarchySchema.addSubClassOf("http://test/Violet","http://test/Cool");
        return hierarchySchema;
    }
}
