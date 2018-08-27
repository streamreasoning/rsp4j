package it.polimi.yasper.core.spe.operators.s2r.execution.assigner;


import it.polimi.yasper.core.rspql.timevarying.TimeVarying;
import it.polimi.yasper.core.spe.tick.Tick;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.operators.r2r.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.time.Time;
import it.polimi.yasper.core.spe.operators.s2r.execution.instance.Window;
import org.apache.commons.rdf.api.Graph;

import java.util.List;

public interface WindowAssigner<E> {

    Report report();

    Tick tick();

    Time time();

    Content<E> getContent(long now);

    List<Content> getContents(long now);

    void report(Report report);

    void tick(Tick timeDriven);

    TimeVarying<E> set(ContinuousQueryExecution content);

    void report_grain(ReportGrain aw);

    void notify(E arg, long ts);

    String iri();

    boolean named();

    Content<E> compute(long t_e, Window w);

}
