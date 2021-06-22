package org.streamreasoning.rsp4j.debs2021.processing.solution;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.api.Triple;
import org.streamreasoning.rsp4j.abstraction.ContinuousProgram;
import org.streamreasoning.rsp4j.abstraction.Task;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.r2r.RelationToRelationOperator;
import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.operators.s2r.syntax.WindowNode;
import org.streamreasoning.rsp4j.api.querying.ContinuousQuery;
import org.streamreasoning.rsp4j.api.sds.SDS;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.report.ReportImpl;
import org.streamreasoning.rsp4j.api.secret.report.strategies.OnWindowClose;
import org.streamreasoning.rsp4j.api.secret.time.TimeFactory;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;
import org.streamreasoning.rsp4j.debs2021.utils.StreamGenerator;
import org.streamreasoning.rsp4j.io.DataStreamImpl;
import org.streamreasoning.rsp4j.yasper.querying.formatter.InstResponseSysOutFormatter;
import org.streamreasoning.rsp4j.yasper.querying.operators.DummyR2R;
import org.streamreasoning.rsp4j.yasper.querying.operators.Rstream;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.TermImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarImpl;
import org.streamreasoning.rsp4j.yasper.querying.operators.r2r.VarOrTerm;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLTimeWindowOperatorFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.WindowNodeImpl;
import org.streamreasoning.rsp4j.yasper.querying.syntax.SimpleRSPQLQuery;
import org.streamreasoning.rsp4j.yasper.sds.SDSImpl;

public class DEBS2021ProcessingSolution {

    public static void main(String[] args) throws InterruptedException {
        StreamGenerator generator = new StreamGenerator();
        DataStream<Graph> inputStream = generator.getStream("stream1");

        DataStreamImpl outStream = new DataStreamImpl("out");


        //ENGINE DEFINITION
        Report report = new ReportImpl();
        report.add(new OnWindowClose());
//        report.add(new NonEmptyContent());
//        report.add(new OnContentChange());
//        report.add(new Periodic());

        Tick tick = Tick.TIME_DRIVEN;
        ReportGrain report_grain = ReportGrain.SINGLE;

        int startTime = 0;
        RDF instance = RDFUtils.getInstance();


        VarOrTerm s = new VarImpl("s");
        VarOrTerm pp = new TermImpl(RDFUtils.createIRI("http://test/hasColor"));
        VarOrTerm o = new TermImpl(RDFUtils.createIRI("http://test/Green"));

        WindowNode wn = new WindowNodeImpl("w1", 2, 2, 0);

        //WINDOW DECLARATION
        StreamToRelationOperatorFactory<Graph, Graph> windowOperatorFactory = new CSPARQLTimeWindowOperatorFactory(TimeFactory.getInstance(), tick, report, report_grain);

        StreamToRelationOp<Graph, Graph> s2r = windowOperatorFactory.build(wn.getRange(), wn.getStep(), startTime);

        //SDS
        SDS<Graph> sds = new SDSImpl();
        //R2R




        ContinuousQuery q = new SimpleRSPQLQuery("q1", inputStream, wn, s, pp, o);

        RelationToRelationOperator<Graph, Triple> r2r = new DummyR2R(sds, q);


        Task<Graph, Graph, Triple,Triple> t =
                new Task.TaskBuilder()
                        .addS2R("stream1", s2r, "w1")
                        .addR2R("w1", r2r)
                        .addR2S("out", new Rstream<Triple,Triple>())
                        .build();
        ContinuousProgram<Graph, Graph, Triple, Triple> cp = new ContinuousProgram.ContinuousProgramBuilder()
                .in(inputStream)
                .setSDS(sds)
                .addTask(t)
                .out(outStream)
                .build();

        outStream.addConsumer(new InstResponseSysOutFormatter("TTL", true));



        inputStream.addConsumer(new InstResponseSysOutFormatter<Graph>("TTL", true));
        generator.startStreaming();
        Thread.sleep(10000);
        generator.stopStreaming();
    }

}
