package it.polimi.yasper.core.spe.report;


import it.polimi.yasper.core.spe.report.strategies.ReportingStrategy;
import it.polimi.yasper.core.spe.windowing.Window;

import java.util.ArrayList;
import java.util.List;

public class ReportImpl implements Report {

    List<ReportingStrategy> strategies = new ArrayList<>();

    @Override
    public boolean report(Window w, long tapp, long tsys) {
        return strategies.stream().allMatch(strategy -> strategy.match(w, tapp, tsys));
    }

    @Override
    public void add(ReportingStrategy r) {
        strategies.add(r);
    }


}
