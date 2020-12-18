package it.polimi.deib.sr.rsp.api.secret.report.strategies;


import it.polimi.deib.sr.rsp.api.secret.content.Content;
import it.polimi.deib.sr.rsp.api.secret.content.EmptyGraphContent;
import it.polimi.deib.sr.rsp.api.operators.s2r.execution.instance.Window;

/**
 * Non-empty content (Rne): reporting is done
 * for t only if the content at t is not empty.
 **/
public class NonEmptyContent implements ReportingStrategy {

    @Override
    public boolean match(Window w, Content c, long tapp, long tsys) {
        return c.size() > 0 || (c instanceof EmptyGraphContent);
    }

}
