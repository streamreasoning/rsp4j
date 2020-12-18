package it.polimi.deib.sr.rsp.yasper.querying.operators.windowing;

import it.polimi.deib.sr.rsp.api.operators.s2r.StreamToRelationOperatorFactory;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.assigner.StreamToRelationOp;
import it.polimi.deib.sr.rsp.api.sds.SDS;
import it.polimi.deib.sr.rsp.api.sds.timevarying.TimeVarying;
import it.polimi.deib.sr.rsp.api.secret.report.Report;
import it.polimi.deib.sr.rsp.api.enums.ReportGrain;
import it.polimi.deib.sr.rsp.api.enums.Tick;
import it.polimi.deib.sr.rsp.api.secret.time.Time;
import it.polimi.deib.sr.rsp.api.stream.data.WebDataStream;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

public class CSPARQLTimeWindowOperator implements StreamToRelationOperatorFactory<Graph, Graph> {

    private final long a, b, t0;
    private final IRI iri;
    private final Time time;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;
    private final SDS<Graph> context;

    public CSPARQLTimeWindowOperator(IRI iri, long a, long b, long t0, Time time, Tick tick, Report report, ReportGrain grain, SDS<Graph> context) {
        this.iri = iri;
        this.a = a;
        this.b = b;
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
        StreamToRelationOp<Graph, Graph> windowStreamToRelationOp = new CSPARQLStreamToRelationOp(iri, a, b, time, tick, report, grain);
        s.addConsumer(windowStreamToRelationOp);
        return windowStreamToRelationOp.set(context);
    }
}
