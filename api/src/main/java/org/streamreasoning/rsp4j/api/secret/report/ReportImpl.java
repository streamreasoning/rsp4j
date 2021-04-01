package org.streamreasoning.rsp4j.api.secret.report;


import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.strategies.ReportingStrategy;
import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;

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
