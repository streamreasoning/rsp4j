package it.polimi.yasper.core.spe.report;


import it.polimi.yasper.core.spe.content.Content;
import it.polimi.yasper.core.spe.report.strategies.ReportingStrategy;
import it.polimi.yasper.core.spe.windowing.definition.Window;

import java.util.ArrayList;
import java.util.List;

public class ReportImpl implements Report {

    List<ReportingStrategy> strategies = new ArrayList<>();

    @Override
    public boolean report(Window w, Content c, long tapp, long tsys) {
        return strategies.stream().allMatch(strategy -> strategy.match(w, c, tapp, tsys));
    }

    @Override
    public void add(ReportingStrategy r) {
        strategies.add(r);
    }


}
