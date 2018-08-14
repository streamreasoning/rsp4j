package it.polimi.yasper.core.spe.windowing.assigner;


import it.polimi.yasper.core.quering.TimeVarying;
import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.content.viewer.View;
import it.polimi.yasper.core.spe.report.Report;
import it.polimi.yasper.core.spe.report.ReportGrain;
import it.polimi.yasper.core.spe.scope.Tick;
import it.polimi.yasper.core.stream.StreamElement;

import java.util.List;

public interface WindowAssigner {

    Report getReport();

    Tick getTick();

    Content getContent(long now);

    List<Content> getContents(long now);

    void setReport(Report report);

    void setTick(Tick timeDriven);

    <T> TimeVarying<T> setView(View content);

    void setReportGrain(ReportGrain aw);

    void notify(StreamElement arg);


}
