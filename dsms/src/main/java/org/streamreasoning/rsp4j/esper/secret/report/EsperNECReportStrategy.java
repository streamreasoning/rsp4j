package org.streamreasoning.rsp4j.esper.secret.report;


import org.streamreasoning.rsp4j.api.operators.s2r.execution.instance.Window;
import org.streamreasoning.rsp4j.api.secret.content.Content;
import org.streamreasoning.rsp4j.api.secret.report.strategies.ReportingStrategy;

public class EsperNECReportStrategy implements ReportingStrategy {

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return c.size() != 0;
    }

}
