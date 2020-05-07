package it.polimi.jasper.secret.report;

import it.polimi.yasper.core.operators.s2r.execution.instance.Window;
import it.polimi.yasper.core.secret.content.Content;
import it.polimi.yasper.core.secret.report.strategies.ReportingStrategy;

public class EsperNECReportStrategy implements ReportingStrategy {

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return c.size() != 0;
    }

}
