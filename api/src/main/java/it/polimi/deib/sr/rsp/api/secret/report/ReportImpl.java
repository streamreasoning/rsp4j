package it.polimi.deib.sr.rsp.api.secret.report;


import it.polimi.deib.sr.rsp.api.secret.content.Content;
import it.polimi.deib.sr.rsp.api.secret.report.strategies.ReportingStrategy;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.instance.Window;

import java.util.ArrayList;
import java.util.List;

public class ReportImpl implements Report {

    List<ReportingStrategy> strategies = new ArrayList<>();

    @Override
    public boolean report(Window w, Content<?, ?> c, long tapp, long tsys) {
        return strategies.stream().allMatch(strategy -> strategy.match(w, c, tapp, tsys));
    }

    @Override
    public void add(ReportingStrategy r) {
        strategies.add(r);
    }

    @Override
    public ReportingStrategy[] strategies() {
        return strategies.toArray(new ReportingStrategy[strategies.size()]);
    }


}
