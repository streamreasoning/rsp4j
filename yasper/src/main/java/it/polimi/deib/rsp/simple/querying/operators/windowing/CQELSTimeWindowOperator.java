package it.polimi.deib.rsp.simple.querying.operators.windowing;

import it.polimi.yasper.core.operators.s2r.StreamToRelationOperator;
import it.polimi.yasper.core.operators.s2r.execution.assigner.Assigner;
import it.polimi.yasper.core.sds.SDS;
import it.polimi.yasper.core.sds.timevarying.TimeVarying;
import it.polimi.yasper.core.secret.report.Report;
import it.polimi.yasper.core.enums.ReportGrain;
import it.polimi.yasper.core.enums.Tick;
import it.polimi.yasper.core.secret.time.Time;
import it.polimi.yasper.core.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

public class CQELSTimeWindowOperator implements StreamToRelationOperator<Graph, Graph> {

    private final long a, t0;
    private final IRI iri;
    private final Time time;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;
    private SDS<Graph> context;

    public CQELSTimeWindowOperator(IRI iri, long a, long t0, Time time, Tick tick, Report report, ReportGrain grain, SDS<Graph> context) {
        this.iri = iri;
        this.a = a;
        this.t0 = t0;
        this.time = time;
        this.tick = tick;
        this.report = report;
        this.grain = grain;
        this.context=context;
    }

    @Override
    public String iri() {
        return iri.getIRIString();
    }

    @Override
    public boolean named() {
        return iri != null;
    }

    @Override
    public TimeVarying<Graph> apply(WebDataStream<Graph> s) {
        Assigner<Graph, Graph> windowAssigner = new CQELSWindowAssigner(iri, a, time, tick, report, grain);
        s.addConsumer(windowAssigner);
        return windowAssigner.set(this.context);
    }
}
