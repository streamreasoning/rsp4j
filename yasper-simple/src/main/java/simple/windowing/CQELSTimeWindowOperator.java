package simple.windowing;

import it.polimi.yasper.core.spe.operators.s2r.WindowOperator;
import it.polimi.yasper.core.spe.operators.s2r.execution.assigner.WindowAssigner;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.stream.RegisteredStream;
import org.apache.commons.rdf.api.Graph;
import org.apache.commons.rdf.api.IRI;

public class CQELSTimeWindowOperator implements WindowOperator<Graph, Graph> {

    private final long a, t0;
    private final IRI iri;
    private final Time time;
    private final Tick tick;
    private final Report report;
    private final ReportGrain grain;

    public CQELSTimeWindowOperator(IRI iri, long a, long t0, Time time, Tick tick, Report report, ReportGrain grain) {
        this.iri = iri;
        this.a = a;
        this.t0 = t0;
        this.time = time;
        this.tick = tick;
        this.report=report;
        this.grain=grain;
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
    public WindowAssigner<Graph, Graph> apply(RegisteredStream<Graph> s) {
        WindowAssigner<Graph, Graph> windowAssigner = new CQELSWindowAssigner(iri, a, t0, time, tick, report, grain);
        s.addWindowAssiger(windowAssigner);
        return windowAssigner;
    }
}
