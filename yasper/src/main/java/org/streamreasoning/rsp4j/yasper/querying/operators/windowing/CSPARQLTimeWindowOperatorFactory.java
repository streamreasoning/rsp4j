package org.streamreasoning.rsp4j.yasper.querying.operators.windowing;

import org.streamreasoning.rsp4j.api.operators.s2r.StreamToRelationOperatorFactory;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.querying.ContinuousQueryExecution;
import org.streamreasoning.rsp4j.api.sds.timevarying.TimeVarying;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.Triple;

public class CSPARQLTimeWindowOperatorFactory implements StreamToRelationOperatorFactory<Graph, Graph> {

    private final long a, b, t0;
    private final Time time;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;
    private ContinuousQueryExecution<Graph, Graph, Triple> context;

    public CSPARQLTimeWindowOperatorFactory(long a, long b, long t0, Time time, Tick tick, Report report, ReportGrain grain,ContinuousQueryExecution<Graph, Graph, Triple> context) {
        this.a = a;
        this.b = b;
        this.t0 = t0;
        this.time = time;
        this.tick = tick;
        this.report = report;
        this.grain = grain;
        this.context = context;
    }


    @Override
    public TimeVarying<Graph> apply(WebDataStream<Graph> s, IRI iri) {
        StreamToRelationOp<Graph, Graph> windowStreamToRelationOp = new CSPARQLStreamToRelationOp(iri, a, b, time, tick, report, grain);
        s.addConsumer(windowStreamToRelationOp);
        context.add(windowStreamToRelationOp);
        return windowStreamToRelationOp.get();
    }
}
