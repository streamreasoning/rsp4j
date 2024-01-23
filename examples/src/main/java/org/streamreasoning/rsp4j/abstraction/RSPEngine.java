package org.streamreasoning.rsp4j.abstraction;

import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;
import org.streamreasoning.rsp4j.api.RDFUtils;
import org.streamreasoning.rsp4j.api.enums.ReportGrain;
import org.streamreasoning.rsp4j.api.enums.Tick;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.assigner.StreamToRelationOp;
import org.streamreasoning.rsp4j.api.secret.report.Report;
import org.streamreasoning.rsp4j.api.secret.time.Time;
import org.streamreasoning.rsp4j.yasper.content.GraphContentFactory;
import org.streamreasoning.rsp4j.yasper.querying.operators.windowing.CSPARQLStreamToRelationOp;

public class RSPEngine {
    private final Report report;
    private final ReportGrain report_grain;
    private final Tick tick;
    private final Time instance;

    public RSPEngine(Time instance, Tick tick, ReportGrain report_grain, Report report) {
        this.instance = instance;
        this.tick = tick;
        this.report_grain = report_grain;
        this.report = report;
    }

    public StreamToRelationOp<Graph, Graph> createCSparqlWindow(IRI windowName, int width, int slide) {
        return new CSPARQLStreamToRelationOp<>(
                windowName,
                width,
                slide,
                instance,
                tick,
                report,
                report_grain,
                new GraphContentFactory(instance));
    }
}
