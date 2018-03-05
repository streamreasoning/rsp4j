package it.polimi.yasper.core.spe.windowing.assigner;


import it.polimi.yasper.core.simple.windowing.TimeVaryingGraph;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;

import java.util.List;

public interface WindowAssigner {

    Report getReport();

    Tick getTick();

    Content getContent(long now);

    List<Content> getContents(long now);

    void setReport(Report report);

    void setTick(Tick timeDriven);

    TimeVaryingGraph setView(View content);

    void setReportGrain(ReportGrain aw);


}
