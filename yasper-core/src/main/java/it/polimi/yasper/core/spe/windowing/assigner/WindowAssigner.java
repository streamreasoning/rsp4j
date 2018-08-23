package it.polimi.yasper.core.spe.windowing.assigner;


import it.polimi.yasper.core.rspql.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.spe.Tick;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;

import java.util.List;

public interface WindowAssigner<E> {

    Report report();

    Tick tick();

    Content<E> getContent(long now);

    List<Content> getContents(long now);

    void report(Report report);

    void tick(Tick timeDriven);

    TimeVarying set(ContinuousQueryExecution content);

    void report_grain(ReportGrain aw);

    void notify(E arg, long ts);

    String iri();

    boolean named();


}
