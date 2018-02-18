package it.polimi.spe.windowing.assigner;


import it.polimi.spe.scope.Tick;
import it.polimi.spe.content.Content;
import it.polimi.spe.content.viewer.View;
import it.polimi.spe.report.Report;
import it.polimi.spe.report.ReportGrain;
import it.polimi.spe.report.strategies.ReportingStrategy;

import java.util.List;

public interface WindowAssigner {

    void addReportingStrategy(ReportingStrategy strategy);

    Report getReport();

    Tick getTick();

    Content getContent(long now);

    List<Content> getContents(long now);

    void setReport(Report report);

    void setTick(Tick timeDriven);

    void setView(View content);

    void setReportGrain(ReportGrain aw);

}
