package it.polimi.yasper.core.spe.windowing.assigner;


import it.polimi.yasper.core.quering.execution.ContinuousQueryExecution;
import it.polimi.yasper.core.quering.rspql.tvg.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.stream.StreamElement;
import it.polimi.yasper.core.Named;

import java.util.List;

public interface WindowAssigner extends Named{

    Report report();

    Tick tick();

    <T> Content<T> getContent(long now);

    List<Content> getContents(long now);

    void report(Report report);

    void tick(Tick timeDriven);

    TimeVarying set(View content);

    TimeVarying set(ContinuousQueryExecution content);

    void report_grain(ReportGrain aw);

    void notify(StreamElement arg);


}
